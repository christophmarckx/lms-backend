package com.switchfully.lmstrapeziumbackend.user.student;

import com.switchfully.lmstrapeziumbackend.classgroup.Classgroup;
import com.switchfully.lmstrapeziumbackend.classgroup.ClassgroupMapper;
import com.switchfully.lmstrapeziumbackend.classgroup.ClassgroupService;
import com.switchfully.lmstrapeziumbackend.codelab.Codelab;
import com.switchfully.lmstrapeziumbackend.codelab.CodelabService;
import com.switchfully.lmstrapeziumbackend.course.CourseMapper;
import com.switchfully.lmstrapeziumbackend.course.dto.CourseSummaryDTO;
import com.switchfully.lmstrapeziumbackend.exception.AccessForbiddenException;
import com.switchfully.lmstrapeziumbackend.exception.UserNotFoundException;
import com.switchfully.lmstrapeziumbackend.progress.CodelabProgress;
import com.switchfully.lmstrapeziumbackend.progress.ProgressService;
import com.switchfully.lmstrapeziumbackend.security.AuthenticationService;
import com.switchfully.lmstrapeziumbackend.security.KeycloakService;
import com.switchfully.lmstrapeziumbackend.user.User;
import com.switchfully.lmstrapeziumbackend.user.UserRepository;
import com.switchfully.lmstrapeziumbackend.user.UserRole;
import com.switchfully.lmstrapeziumbackend.user.UserService;
import com.switchfully.lmstrapeziumbackend.user.dto.CreateStudentDTO;
import com.switchfully.lmstrapeziumbackend.user.dto.StudentDTO;
import com.switchfully.lmstrapeziumbackend.user.dto.StudentWithProgressDTO;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
@Transactional
public class StudentService {
    private final UserRepository userRepository;
    private final KeycloakService keycloakService;
    private final AuthenticationService authenticationService;
    private final CodelabService codelabService;
    private final ProgressService progressService;
    private final ClassgroupService classgroupService;

    public StudentService(UserRepository userRepository,
                          KeycloakService keycloakService,
                          AuthenticationService authenticationService,
                          CodelabService codelabService,
                          ProgressService progressService,
                          ClassgroupService classgroupService) {
        this.userRepository = userRepository;
        this.keycloakService = keycloakService;
        this.authenticationService = authenticationService;
        this.codelabService = codelabService;
        this.progressService = progressService;
        this.classgroupService = classgroupService;

    }

    public StudentDTO createStudent(CreateStudentDTO createStudentDTO) {
        UUID userKeycloakId = this.keycloakService.createUser(createStudentDTO);
        return StudentMapper.toDTO(this.userRepository.save(StudentMapper.toUser(userKeycloakId, createStudentDTO)));
    }

    public StudentDTO getStudentByAuthentication(Authentication authentication, UUID studentId) {

        UUID authenticatedUserId = this.authenticationService.getAuthenticatedUserId(authentication)
                .orElseThrow(UserNotFoundException::new);

        if (!authenticatedUserId.equals(studentId)) {
            throw new AccessForbiddenException();
        }

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

    public List<StudentWithProgressDTO> getStudentsWithProgress(UUID classgroupId) {
        List<User> students;
        if (classgroupId == null) {
            students = this.userRepository.findAllByRole(UserRole.STUDENT);
        } else {
            students = this.userRepository.findAllByClassgroupsAndRole(classgroupService.getById(classgroupId), UserRole.STUDENT);
        }


        return students.stream().map(student -> {
            if (student.getClassgroups().size() == 1) {
                List<Codelab> codelabs = student.getClassgroups().getFirst().getCourse().getModules().stream()
                        .flatMap(module -> codelabService.getCodelabsByModuleId(module.getId()).stream()).toList();

                int totalProgression = codelabs.size();

                int actualProgression = (int) codelabs.stream()
                        .filter(codelab -> progressService.getCodelabProgress(codelab, student).equals(CodelabProgress.DONE))
                        .count();

                return StudentMapper.toStudentWithProgressDTO(
                        student,
                        actualProgression,
                        totalProgression,
                        ClassgroupMapper.toDTO(student.getClassgroups().getFirst()));
            } else {
                return StudentMapper.toStudentWithProgressDTO(
                        student,
                        0,
                        0,
                        null);
            }
        }).toList();
    }
}
