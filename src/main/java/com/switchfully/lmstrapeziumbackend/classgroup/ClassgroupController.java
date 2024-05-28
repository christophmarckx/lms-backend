package com.switchfully.lmstrapeziumbackend.classgroup;

import com.switchfully.lmstrapeziumbackend.classgroup.dto.ClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.CreateClassgroupDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "classgroups")
@CrossOrigin(originPatterns = "http://localhost:4200") //If deployed -> edit
public class ClassgroupController {
    private final Logger logger = LoggerFactory.getLogger(ClassgroupController.class);
    private final ClassgroupService classgroupService;
    //TODO Add security service

    @Autowired
    public ClassgroupController(ClassgroupService classgroupService) {
        this.classgroupService = classgroupService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ClassgroupDTO createClassgroup(@Valid @RequestBody CreateClassgroupDTO createClassgroupDTO) {
        //TODO Add security check
        this.logger.info("POST /classgroups: Creating a classgroup");
        return this.classgroupService.createClassgroup(createClassgroupDTO);
    }
}
