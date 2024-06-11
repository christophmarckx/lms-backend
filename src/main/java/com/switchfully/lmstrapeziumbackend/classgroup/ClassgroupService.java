package com.switchfully.lmstrapeziumbackend.classgroup;

import com.switchfully.lmstrapeziumbackend.classgroup.dto.ClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.ClassgroupWithMembersDTO;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.CreateClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.course.Course;
import com.switchfully.lmstrapeziumbackend.course.CourseService;
import com.switchfully.lmstrapeziumbackend.exception.AccessForbiddenException;
import com.switchfully.lmstrapeziumbackend.exception.ClassgroupNotFoundException;
import com.switchfully.lmstrapeziumbackend.exception.IllegalUserRoleException;
import com.switchfully.lmstrapeziumbackend.exception.UserNotFoundException;
import com.switchfully.lmstrapeziumbackend.security.AuthenticationService;
import com.switchfully.lmstrapeziumbackend.user.User;
import com.switchfully.lmstrapeziumbackend.user.UserRole;
import com.switchfully.lmstrapeziumbackend.user.UserService;
import com.switchfully.lmstrapeziumbackend.user.coach.CoachService;
import com.switchfully.lmstrapeziumbackend.user.dto.CoachDTO;
import com.switchfully.lmstrapeziumbackend.user.dto.StudentDTO;
import com.switchfully.lmstrapeziumbackend.user.student.StudentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClassgroupService {
    private final ClassgroupRepository classgroupRepository;
    private final CourseService courseService;
    private final UserService userService;
    private final StudentService studentService;
    private final CoachService coachService;
    private final AuthenticationService authenticationService;

    public ClassgroupService(ClassgroupRepository classgroupRepository, CourseService courseService, UserService userService, CoachService coachService, StudentService studentService, AuthenticationService authenticationService) {
        this.classgroupRepository = classgroupRepository;
        this.courseService = courseService;
        this.userService = userService;
        this.studentService = studentService;
        this.coachService = coachService;
        this.authenticationService = authenticationService;
    }

    public ClassgroupDTO createClassgroup(CreateClassgroupDTO createClassgroupDTO) {
        Course courseToAddToClass = courseService.getCourseById(createClassgroupDTO.courseId());

        List<UUID> coachIds = new ArrayList<>();
        createClassgroupDTO.coaches().forEach(userId -> {
            if (!coachIds.contains(userId)) {
                coachIds.add(userId);
            }
        });

        List<User> coaches = checkIfUsersAreCoaches(coachIds);

        Classgroup classgroupCreated = classgroupRepository.save(ClassgroupMapper.toClassgroup(
                createClassgroupDTO.name(),
                courseToAddToClass,
                coaches
        ));

        return ClassgroupMapper.toDTO(classgroupCreated);
    }

    private List<User> checkIfUsersAreCoaches(List<UUID> userIds) {
        List<User> users = userIds.stream().map(userService::getUserById).collect(Collectors.toList());
        users.forEach(userToCheck -> {
            if (userToCheck.getRole() != UserRole.COACH) {
                throw new IllegalUserRoleException();
            }
        });
        return users;
    }

    public ClassgroupWithMembersDTO getClassgroupWithMembersDTOById(UUID classgroupId) {
        Classgroup classgroup = this.getById(classgroupId);
        List<StudentDTO> students = this.studentService.getStudentsFollowingClass(classgroup);
        List<CoachDTO> coaches = this.coachService.getCoachesFollowingClass(classgroup);
        return ClassgroupMapper.toDTO(classgroup, students, coaches);
    }

    public Classgroup getById(UUID classgroupId) {
        return this.classgroupRepository
                .findById(classgroupId)
                .orElseThrow(() -> new ClassgroupNotFoundException(classgroupId));
    }

    private List<Classgroup> getAllClassgroups() {
        return this.classgroupRepository.findAll();
    }

    public List<ClassgroupDTO> getAllClassgroupsDTO() {
        return getAllClassgroups()
                .stream()
                .map(ClassgroupMapper::toDTO)
                .collect(Collectors.toList());
    }

    private List<ClassgroupDTO> getClassgroupsForUserId(UUID userId) {
        User myUser = this.userService.getUserById(userId);
        List<Classgroup> classgroups = getAllClassgroups();

        return classgroups.stream()
                .filter(classgroup -> classgroup.getUsers().contains(myUser))
                .map(ClassgroupMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void addStudentToClassGroup(UUID classgroupId, Authentication authentication) {
        User studentToAdd = userService.getUserById(authenticationService
                .getAuthenticatedUserId(authentication)
                .orElseThrow(UserNotFoundException::new));

        Classgroup classgroupToModify = getById(classgroupId);
        classgroupToModify.addUser(studentToAdd);
        classgroupRepository.save(classgroupToModify);
    }

    public List<ClassgroupDTO> getAllClassgroupsForUserId(UUID userId, Authentication authentication) {
        UUID authenticatedUserId = this.authenticationService.getAuthenticatedUserId(authentication).orElseThrow(UserNotFoundException::new);
        UserRole userRoleOfAuthenticated = authenticationService.getAuthenticatedUserRole(authentication);
        if (!authenticatedUserId.equals(userId) && userRoleOfAuthenticated != UserRole.COACH) {
            throw new AccessForbiddenException();
        }
        return getClassgroupsForUserId(userId);
    }
}
