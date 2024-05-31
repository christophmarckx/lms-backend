package com.switchfully.lmstrapeziumbackend.classgroup;

import com.switchfully.lmstrapeziumbackend.classgroup.dto.ClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.ClassgroupWithMembersDTO;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.CreateClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.course.Course;
import com.switchfully.lmstrapeziumbackend.course.CourseService;
import com.switchfully.lmstrapeziumbackend.exception.ClassgroupNotFoundException;
import com.switchfully.lmstrapeziumbackend.user.student.StudentService;
import com.switchfully.lmstrapeziumbackend.user.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClassgroupService {
    private final ClassgroupRepository classgroupRepository;
    private final CourseService courseService;
    private final StudentService studentService;

    @Autowired
    public ClassgroupService(ClassgroupRepository classgroupRepository, CourseService courseService, StudentService studentService) {
        this.classgroupRepository = classgroupRepository;
        this.courseService = courseService;
        this.studentService = studentService;
    }

    public ClassgroupDTO createClassgroup(CreateClassgroupDTO createClassgroupDTO) {

        Course courseToAddToClass = courseService.getCourseById(UUID.fromString(createClassgroupDTO.getCourseId()));

        Classgroup classgroupCreated = classgroupRepository.save(ClassgroupMapper.toClassgroup(
                createClassgroupDTO.getName(),
                courseToAddToClass
        ));
        return ClassgroupMapper.toDTO(classgroupCreated);
    }

    public ClassgroupWithMembersDTO getById(UUID classgroupId) {
        Classgroup classgroup = this.classgroupRepository
                .findById(classgroupId)
                .orElseThrow(() -> new ClassgroupNotFoundException(classgroupId));

        List<StudentDTO> students = this.studentService.getStudentFollowingClass(classgroupId);

        return ClassgroupMapper.toDTO(classgroup, students);
    }

}
