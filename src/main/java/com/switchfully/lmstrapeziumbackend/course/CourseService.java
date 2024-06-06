package com.switchfully.lmstrapeziumbackend.course;

import com.switchfully.lmstrapeziumbackend.codelab.Codelab;
import com.switchfully.lmstrapeziumbackend.codelab.CodelabService;
import com.switchfully.lmstrapeziumbackend.course.dto.*;
import com.switchfully.lmstrapeziumbackend.exception.CourseNotFoundException;
import com.switchfully.lmstrapeziumbackend.module.Module;
import com.switchfully.lmstrapeziumbackend.module.ModuleMapper;
import com.switchfully.lmstrapeziumbackend.module.ModuleService;
import com.switchfully.lmstrapeziumbackend.module.dto.ModuleWithCodelabsDTO;
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

    public CourseService(CourseRepository courseRepository,
                         ModuleService moduleService, CodelabService codelabService) {
        this.courseRepository = courseRepository;
        this.moduleService = moduleService;
        this.codelabService = codelabService;
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

    public CourseWithModulesDTO getCourseWithModulesById(UUID courseId) {
        Course course = this.getCourseById(courseId);
        List<Module> rootModules = this.moduleService.getAllRootModules();
        Module modulesContainer = new Module(rootModules);

        List<ModuleWithCodelabsDTO> moduleWithCodelabsDTOs = new ArrayList<>();

        getModuleWithCodelabsDTOFromModuleInsideACourse(modulesContainer, course)
                .ifPresent(moduleWithCodelabsDTO -> moduleWithCodelabsDTOs.addAll(moduleWithCodelabsDTO.modules()));

        return CourseMapper.toCourseWithModulesDTO(course, moduleWithCodelabsDTOs);
    }

    private Optional<ModuleWithCodelabsDTO> getModuleWithCodelabsDTOFromModuleInsideACourse(Module module, Course course) {
        List<ModuleWithCodelabsDTO> childModuleWithCodelabsDTOs = new ArrayList<>();

        for (Module childModule: module.getChildModules()) {
            getModuleWithCodelabsDTOFromModuleInsideACourse(childModule, course)
                    .ifPresent(moduleWithCodelabsDTO -> {
                        if (moduleWithCodelabsDTO.id() == null) {
                            childModuleWithCodelabsDTOs.addAll(moduleWithCodelabsDTO.modules());
                        }
                        else {
                            childModuleWithCodelabsDTOs.add(moduleWithCodelabsDTO);
                        }
            });
        }
        return constructActualModuleWithCodelabsDTO(module, course, childModuleWithCodelabsDTOs);
    }

    private Optional<ModuleWithCodelabsDTO> constructActualModuleWithCodelabsDTO(Module module, Course course, List<ModuleWithCodelabsDTO> childModuleWithCodelabsDTOs) {
        if (course.getModules().contains(module)) {
            List<Codelab> codelabs = this.codelabService.getCodelabsByModuleId(module.getId());
            return Optional.of(ModuleMapper.toModuleWithCodelabsDTO(module, childModuleWithCodelabsDTOs, codelabs));
        }
        if (!childModuleWithCodelabsDTOs.isEmpty()) {
            return Optional.of(new ModuleWithCodelabsDTO(null, null, childModuleWithCodelabsDTOs, null));
        }
        return Optional.empty();
    }
}
