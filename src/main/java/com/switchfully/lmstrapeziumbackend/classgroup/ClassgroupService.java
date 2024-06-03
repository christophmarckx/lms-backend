package com.switchfully.lmstrapeziumbackend.classgroup;

import com.switchfully.lmstrapeziumbackend.classgroup.dto.ClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.CreateClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.course.Course;
import com.switchfully.lmstrapeziumbackend.course.CourseService;
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
    //
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public ClassgroupService(ClassgroupRepository classgroupRepository, CourseService courseService, UserService userService,
                             UserRepository userRepository) {
        this.classgroupRepository = classgroupRepository;
        this.courseService = courseService;
        this.userService = userService;
        this.userRepository = userRepository;
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
}
