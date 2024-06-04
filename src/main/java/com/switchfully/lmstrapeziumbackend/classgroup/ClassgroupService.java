package com.switchfully.lmstrapeziumbackend.classgroup;

import com.switchfully.lmstrapeziumbackend.classgroup.dto.ClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.ClassgroupWithMembersDTO;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.CreateClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.course.Course;
import com.switchfully.lmstrapeziumbackend.course.CourseService;
import com.switchfully.lmstrapeziumbackend.exception.ClassgroupNotFoundException;
import com.switchfully.lmstrapeziumbackend.user.coach.CoachService;
import com.switchfully.lmstrapeziumbackend.user.dto.CoachDTO;
import com.switchfully.lmstrapeziumbackend.user.student.StudentService;
import com.switchfully.lmstrapeziumbackend.user.dto.StudentDTO;
import com.switchfully.lmstrapeziumbackend.user.User;
import com.switchfully.lmstrapeziumbackend.user.UserRepository;
import com.switchfully.lmstrapeziumbackend.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClassgroupService {
    private final ClassgroupRepository classgroupRepository;
    private final CourseService courseService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final StudentService studentService;
    private final CoachService coachService;

    @Autowired
    public ClassgroupService(ClassgroupRepository classgroupRepository, CourseService courseService, UserService userService,
                             UserRepository userRepository, CoachService coachService, StudentService studentService) {
        this.classgroupRepository = classgroupRepository;
        this.courseService = courseService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.studentService = studentService;
        this.coachService = coachService;
    }

    public ClassgroupDTO createClassgroup(CreateClassgroupDTO createClassgroupDTO) {

        Course courseToAddToClass = courseService.getCourseById(UUID.fromString(createClassgroupDTO.getCourseId()));

        List<User> coachs =  createClassgroupDTO.getCoachs().stream()
                .map(userService::getUserById).collect(Collectors.toList());

        Classgroup classgroupCreated = classgroupRepository.save(ClassgroupMapper.toClassgroup(
                createClassgroupDTO.getName(),
                courseToAddToClass,
                coachs
        ));
        for (User coach: coachs){
            userService.addClassGroupToUser(classgroupCreated,coach);
        }

        return ClassgroupMapper.toDTO(classgroupCreated);
    }

    public ClassgroupWithMembersDTO getClassgroupWithMembersDTOById(UUID classgroupId) {
        Classgroup classgroup = this.getById(classgroupId);
        List<StudentDTO> students = this.studentService.getStudentFollowingClass(classgroup);
        List<CoachDTO> coaches = this.coachService.getCoachesFollowingClass(classgroup);
        return ClassgroupMapper.toDTO(classgroup, students, coaches);
    }

    public Classgroup getById(UUID classgroupId){
        return this.classgroupRepository
                .findById(classgroupId)
                .orElseThrow(() -> new ClassgroupNotFoundException(classgroupId));
    }

}
