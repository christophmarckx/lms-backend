package com.switchfully.lmstrapeziumbackend.module;

import com.switchfully.lmstrapeziumbackend.module.dto.CreateModuleDTO;
import com.switchfully.lmstrapeziumbackend.module.dto.ModuleDTO;
import com.switchfully.lmstrapeziumbackend.module.dto.ModuleWithCoursesDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/{id}")
    ModuleWithCoursesDTO getModuleById(@PathVariable UUID id) {
        return moduleService.getModuleWithCoursesById(id);
    }

    @PostMapping
    public ModuleDTO addModule(@RequestBody @Valid CreateModuleDTO createModuleDTO) {
        return this.moduleService.createModule(createModuleDTO);
    }

}
