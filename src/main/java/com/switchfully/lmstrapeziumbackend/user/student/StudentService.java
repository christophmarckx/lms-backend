package com.switchfully.lmstrapeziumbackend.user.student;

import com.switchfully.lmstrapeziumbackend.classgroup.Classgroup;
import com.switchfully.lmstrapeziumbackend.course.CourseMapper;
import com.switchfully.lmstrapeziumbackend.course.dto.CourseSummaryDTO;
import com.switchfully.lmstrapeziumbackend.exception.AccessForbiddenException;
import com.switchfully.lmstrapeziumbackend.exception.UserNotFoundException;
import com.switchfully.lmstrapeziumbackend.security.AuthenticationService;
import com.switchfully.lmstrapeziumbackend.security.KeycloakService;
import com.switchfully.lmstrapeziumbackend.user.User;
import com.switchfully.lmstrapeziumbackend.user.UserRepository;
import com.switchfully.lmstrapeziumbackend.user.UserRole;
import com.switchfully.lmstrapeziumbackend.user.UserService;
import com.switchfully.lmstrapeziumbackend.user.dto.CreateStudentDTO;
import com.switchfully.lmstrapeziumbackend.user.dto.StudentDTO;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class StudentService {
    private final UserRepository userRepository;
    private final KeycloakService keycloakService;
    private final AuthenticationService authenticationService;
    private final UserService userService;

    public StudentService(UserRepository userRepository, KeycloakService keycloakService, AuthenticationService authenticationService, UserService userService) {
        this.userRepository = userRepository;
        this.keycloakService = keycloakService;
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    public StudentDTO createStudent(CreateStudentDTO createStudentDTO) {
        UUID userKeycloakId = this.keycloakService.createUser(createStudentDTO);
        return StudentMapper.toDTO(this.userRepository.save(StudentMapper.toUser(userKeycloakId, createStudentDTO)));
    }

    public List<StudentDTO> getStudentsFollowingClass(Classgroup classgroup) {
        return this.userRepository
                .findAllByClassgroupsAndRole(classgroup, UserRole.STUDENT)
                .stream().map(StudentMapper::toDTO)
                .toList();
    }

    public StudentDTO getStudentByAuthentication(Authentication authentication/*, UUID studentId*/) {

        UUID authenticatedUserId = this.authenticationService.getAuthenticatedUserId(authentication)
                .orElseThrow(UserNotFoundException::new);

        Optional<User> userOptional = userRepository.findById(authenticatedUserId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }

        return StudentMapper.toDTO(userOptional.get());
    }

    public Optional<CourseSummaryDTO> getCourseFollowedByStudentId(Authentication authentication, UUID studentId) {
        UserRole authenticatedUserRole = authenticationService.getAuthenticatedUserRole(authentication);

        if (authenticatedUserRole.equals(UserRole.STUDENT)) {
            UUID authenticatedUserId = authenticationService.getAuthenticatedUserId(authentication)
                    .orElseThrow(UserNotFoundException::new);

            if (!authenticatedUserId.equals(studentId)) {
                throw new AccessForbiddenException();
            }
        }

        User student = userRepository.findById(studentId)
                .orElseThrow(UserNotFoundException::new);

        if (student.getClassgroups().isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(CourseMapper.toCourseSummaryDTO(student.getClassgroups().getFirst().getCourse()));
    }
}
