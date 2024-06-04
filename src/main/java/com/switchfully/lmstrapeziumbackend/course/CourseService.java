package com.switchfully.lmstrapeziumbackend.course;

import com.switchfully.lmstrapeziumbackend.codelab.Codelab;
import com.switchfully.lmstrapeziumbackend.codelab.CodelabMapper;
import com.switchfully.lmstrapeziumbackend.codelab.CodelabService;
import com.switchfully.lmstrapeziumbackend.course.dto.CourseDTO;
import com.switchfully.lmstrapeziumbackend.course.dto.CourseWithModulesDTO;
import com.switchfully.lmstrapeziumbackend.course.dto.CreateCourseDTO;
import com.switchfully.lmstrapeziumbackend.course.dto.UpdateCourseDTO;
import com.switchfully.lmstrapeziumbackend.exception.CourseNotFoundException;
import com.switchfully.lmstrapeziumbackend.module.ModuleRepository;
import com.switchfully.lmstrapeziumbackend.module.Module;
import com.switchfully.lmstrapeziumbackend.module.ModuleService;
import com.switchfully.lmstrapeziumbackend.module.dto.ModuleWithCodelabsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
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

    public List<CourseDTO> getAllCourses() {
            return CourseMapper.toDTO(courseRepository.findAll());
    }

    @Transactional
    public CourseDTO updateCourse(UUID courseId, UpdateCourseDTO updateCourseDTO) {
        Course courseFound = getCourseById(courseId);
        courseFound.updateCourseName(updateCourseDTO.name());
        return CourseMapper.toDTO(courseFound);
    }

    public CourseWithModulesDTO getCourseWithModulesById(UUID courseId) {
        Course course = this.getCourseById(courseId);
        List<ModuleWithCodelabsDTO> moduleWithCodelabsDTOs = new ArrayList<>();
        course.getModules().forEach(module -> {
            List<Codelab> codelabs = this.codelabService.getCodelabsByModuleId(module.getId());
            moduleWithCodelabsDTOs.add(new ModuleWithCodelabsDTO(module.getId(), module.getName(), CodelabMapper.toDTO(codelabs)));
        });
        return new CourseWithModulesDTO(course.getId(),course.getName(), moduleWithCodelabsDTOs);
    }
}
