package com.switchfully.lmstrapeziumbackend.module;

import com.switchfully.lmstrapeziumbackend.module.dto.ModuleDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/modules")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping
    List<ModuleDTO> getAllModules() {
        return moduleService.getAllModules();
    }
}
