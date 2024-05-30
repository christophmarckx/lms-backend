package com.switchfully.lmstrapeziumbackend.module;


import com.switchfully.lmstrapeziumbackend.exception.ModuleDoesNotExistException;
import com.switchfully.lmstrapeziumbackend.module.dto.CreateModuleDTO;
import com.switchfully.lmstrapeziumbackend.module.dto.ModuleDTO;
import com.switchfully.lmstrapeziumbackend.module.dto.ModuleWithCoursesDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.switchfully.lmstrapeziumbackend.module.Module;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ModuleService {
    private ModuleRepository moduleRepository;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public List<ModuleDTO> getAllModules() {
        return moduleRepository.findAll()
                .stream()
                .map(ModuleMapper::toDTO)
                .toList();
    }

    public ModuleDTO createModule(CreateModuleDTO createModuleDTO) {
        Module parent = null;
        if (createModuleDTO.parentModuleId() != null){
            parent = moduleRepository.findById(createModuleDTO.parentModuleId())
                .orElseThrow(() -> new ModuleDoesNotExistException(createModuleDTO.parentModuleId()));
        }
        return ModuleMapper.toDTO(moduleRepository.save(ModuleMapper.toModule(createModuleDTO, parent)));
    }

    public ModuleWithCoursesDTO getModuleById(UUID id) {
        return ModuleMapper.toModuleWithCoursesDTO(moduleRepository.findById(id)
                .orElseThrow(() -> new ModuleDoesNotExistException(id)));
    }

}
