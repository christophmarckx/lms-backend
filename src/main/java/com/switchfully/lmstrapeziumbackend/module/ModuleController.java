package com.switchfully.lmstrapeziumbackend.module;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/modules")
public class ModuleController {

    @GetMapping
    void getAllModules() {
        System.out.println("wow, wink");
    }
}
