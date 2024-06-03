package com.switchfully.lmstrapeziumbackend.module;


import com.switchfully.lmstrapeziumbackend.exception.ModuleDoesNotExistException;
import com.switchfully.lmstrapeziumbackend.module.dto.CreateModuleDTO;
import com.switchfully.lmstrapeziumbackend.module.dto.ModuleDTO;
import com.switchfully.lmstrapeziumbackend.module.dto.ModuleWithCoursesDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public ModuleWithCoursesDTO getModuleWithCoursesById(UUID id) {
        return ModuleMapper.toModuleWithCoursesDTO(getModuleById(id));
    }

    public List<Module> getModulesByIds(List<UUID> ids) {
        if (ids == null) {
            return new ArrayList<>();
        }
        return ids.stream().map(this::getModuleById).toList();
    }

    public Module getModuleById(UUID id) {
        return moduleRepository.findById(id)
                .orElseThrow(() -> new ModuleDoesNotExistException(id));
    }
}
