package com.switchfully.lmstrapeziumbackend.classgroup;

import com.switchfully.lmstrapeziumbackend.classgroup.dto.ClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.ClassgroupWithMembersDTO;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.CreateClassgroupDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "classgroups")
public class ClassgroupController {
    private final Logger logger = LoggerFactory.getLogger(ClassgroupController.class);
    private final ClassgroupService classgroupService;


    @Autowired
    public ClassgroupController(ClassgroupService classgroupService) {
        this.classgroupService = classgroupService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ClassgroupDTO createClassgroup(@Valid @RequestBody CreateClassgroupDTO createClassgroupDTO) {
        this.logger.info("POST /classgroups: Creating a classgroup");
        return this.classgroupService.createClassgroup(createClassgroupDTO);
    }

    @GetMapping(produces = "application/json", path = "{classgroupId}")
    @ResponseStatus(HttpStatus.OK)
    public ClassgroupWithMembersDTO getClassgroup(@PathVariable UUID classgroupId) {
        this.logger.info("GET /classgroups Getting a classgroup by id");
        return this.classgroupService.getClassgroupWithMembersDTOById(classgroupId);
    }

    @PutMapping(path = "/{classgroupId}/add-student")
    @ResponseStatus(HttpStatus.OK)
    public void addStudentToClass(@PathVariable UUID classgroupId, Authentication authentication) {
        this.logger.info("PUT /classgroups/{classgroupId}/add-student: adding a student to a classgroup");
        classgroupService.addStudentToClassGroup(classgroupId, authentication);
    }
}
