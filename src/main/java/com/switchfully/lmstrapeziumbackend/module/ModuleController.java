package com.switchfully.lmstrapeziumbackend.module;

import com.switchfully.lmstrapeziumbackend.module.dto.CreateModuleDTO;
import com.switchfully.lmstrapeziumbackend.module.dto.ModuleDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ModuleDTO addModule(@RequestBody @Valid CreateModuleDTO createModuleDTO) {
        return this.moduleService.createModule(createModuleDTO);
    }

}
