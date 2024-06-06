package com.switchfully.lmstrapeziumbackend.course;

import com.switchfully.lmstrapeziumbackend.codelab.Codelab;
import com.switchfully.lmstrapeziumbackend.codelab.CodelabService;
import com.switchfully.lmstrapeziumbackend.course.dto.*;
import com.switchfully.lmstrapeziumbackend.exception.CourseNotFoundException;
import com.switchfully.lmstrapeziumbackend.exception.UserNotFoundException;
import com.switchfully.lmstrapeziumbackend.module.Module;
import com.switchfully.lmstrapeziumbackend.module.ModuleMapper;
import com.switchfully.lmstrapeziumbackend.module.ModuleService;
import com.switchfully.lmstrapeziumbackend.module.dto.ModuleWithCodelabsDTO;
import com.switchfully.lmstrapeziumbackend.progress.CodelabProgress;
import com.switchfully.lmstrapeziumbackend.progress.ProgressService;
import com.switchfully.lmstrapeziumbackend.security.AuthenticationService;
import com.switchfully.lmstrapeziumbackend.user.User;
import com.switchfully.lmstrapeziumbackend.user.UserRole;
import com.switchfully.lmstrapeziumbackend.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CourseService {
    private final CourseRepository courseRepository;
    private final ModuleService moduleService;
    private final CodelabService codelabService;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final ProgressService progressService;

    public CourseService(CourseRepository courseRepository,
                         ModuleService moduleService, CodelabService codelabService, AuthenticationService authenticationService, UserService userService, ProgressService progressService) {
        this.courseRepository = courseRepository;
        this.moduleService = moduleService;
        this.codelabService = codelabService;
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.progressService = progressService;
    }

    public CourseDTO createCourse(CreateCourseDTO createCourseDTO) {
        List<Module> modules = moduleService.getModulesByIds(createCourseDTO.moduleIds());
        Course courseCreated = courseRepository.save(CourseMapper.toCourse(createCourseDTO, modules));
        return CourseMapper.toDTO(courseCreated);
    }

    public Course getCourseById(UUID courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty()) {
            throw new CourseNotFoundException();
        }
        return courseOptional.get();
    }

    public CourseDTO getCourseDTOById(UUID courseId) {
        return CourseMapper.toDTO(this.getCourseById(courseId));
    }

    public List<CourseSummaryDTO> getAllSummaryCourses() {
        return CourseMapper.toCourseSummaryDTO(courseRepository.findAll());
    }

    public CourseDTO updateCourse(UUID courseId, UpdateCourseDTO updateCourseDTO) {
        Course courseFound = getCourseById(courseId);
        courseFound.updateCourseName(updateCourseDTO.name());
        return CourseMapper.toDTO(courseFound);
    }

    public CourseWithModulesDTO getCourseWithModulesById(Authentication authentication, UUID courseId) {
        boolean needCodelabsProgression = false;
        User student = null;
        Course course = this.getCourseById(courseId);

        if (authenticationService.getAuthenticatedUserRole(authentication).equals(UserRole.STUDENT)) {
            UUID studentID = authenticationService.getAuthenticatedUserId(authentication)
                    .orElseThrow(UserNotFoundException::new);

            student = userService.getUserById(studentID);

            if (student.getClassgroups().getFirst().getCourse().equals(course)) {
                needCodelabsProgression = true;
            }
        }

        List<Module> rootModules = this.moduleService.getAllRootModules();
        Module modulesContainer = new Module(rootModules);

        List<ModuleWithCodelabsDTO> moduleWithCodelabsDTOs = new ArrayList<>();

        getModuleWithCodelabsDTOFromModuleInsideACourse(modulesContainer, course, needCodelabsProgression, student)
                .ifPresent(moduleWithCodelabsDTO -> moduleWithCodelabsDTOs.addAll(moduleWithCodelabsDTO.modules()));

        return CourseMapper.toCourseWithModulesDTO(course, moduleWithCodelabsDTOs);
    }

    private Optional<ModuleWithCodelabsDTO> getModuleWithCodelabsDTOFromModuleInsideACourse(Module module, Course course, boolean needCodelabsProgression, User student) {
        List<ModuleWithCodelabsDTO> childModuleWithCodelabsDTOs = new ArrayList<>();

        for (Module childModule: module.getChildModules()) {
            getModuleWithCodelabsDTOFromModuleInsideACourse(childModule, course, needCodelabsProgression, student)
                    .ifPresent(moduleWithCodelabsDTO -> {
                        if (moduleWithCodelabsDTO.id() == null) {
                            childModuleWithCodelabsDTOs.addAll(moduleWithCodelabsDTO.modules());
                        }
                        else {
                            childModuleWithCodelabsDTOs.add(moduleWithCodelabsDTO);
                        }
            });
        }
        return constructActualModuleWithCodelabsDTO(module, course, childModuleWithCodelabsDTOs, needCodelabsProgression, student);
    }

    private Optional<ModuleWithCodelabsDTO> constructActualModuleWithCodelabsDTO(Module module, Course course, List<ModuleWithCodelabsDTO> childModuleWithCodelabsDTOs, boolean needCodelabsProgression, User student) {
        if (course.getModules().contains(module)) {
            List<Codelab> codelabs = this.codelabService.getCodelabsByModuleId(module.getId());
            if (needCodelabsProgression) {
                List<CodelabProgress> codelabProgresses = codelabs.stream().map(codelab -> progressService.getCodelabProgress(codelab, student)).toList();
                return Optional.of(ModuleMapper.toModuleWithCodelabsDTO(module, childModuleWithCodelabsDTOs, codelabs, codelabProgresses));
            }
            return Optional.of(ModuleMapper.toModuleWithCodelabsDTO(module, childModuleWithCodelabsDTOs, codelabs));
        }
        if (!childModuleWithCodelabsDTOs.isEmpty()) {
            return Optional.of(new ModuleWithCodelabsDTO(null, null, childModuleWithCodelabsDTOs, null));
        }
        return Optional.empty();
    }
}
