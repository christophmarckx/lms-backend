package com.switchfully.lmstrapeziumbackend.classgroup;

import com.switchfully.lmstrapeziumbackend.classgroup.dto.ClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.CreateClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.course.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassgroupService {
    private final ClassgroupRepository classgroupRepository;

    @Autowired
    public ClassgroupService(ClassgroupRepository classgroupRepository) {
        this.classgroupRepository = classgroupRepository;
    }

    public ClassgroupDTO createClassgroup(CreateClassgroupDTO createClassgroupDTO) {
        //Get course
        Classgroup classgroupCreated = classgroupRepository.save(ClassgroupMapper.toClassgroup(
                createClassgroupDTO.getName(),
                new Course() //TODO -> Get course from db with id passed in CreateClassgroupDTO
        ));
        return ClassgroupMapper.toDTO(classgroupCreated);
    }
}
