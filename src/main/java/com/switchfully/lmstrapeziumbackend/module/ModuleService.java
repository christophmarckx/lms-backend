package com.switchfully.lmstrapeziumbackend.module;


import com.switchfully.lmstrapeziumbackend.exception.ModuleDoesNotExistException;
import com.switchfully.lmstrapeziumbackend.module.dto.CreateModuleDTO;
import com.switchfully.lmstrapeziumbackend.module.dto.ModuleDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ModuleService {
    private ModuleRepository moduleRepository;
    private ModuleMapper moduleMapper;

    public ModuleService(ModuleRepository moduleRepository, ModuleMapper moduleMapper) {
        this.moduleRepository = moduleRepository;
        this.moduleMapper = moduleMapper;
    }

    public List<ModuleDTO> getAllModules() {
        return moduleRepository.findAll()
                .stream()
                .map(moduleToConvert -> moduleMapper.toDTO(moduleToConvert))
                .toList();
    }

    public ModuleDTO createModule(CreateModuleDTO createModuleDTO) {
        Module parent = null;
        if (createModuleDTO.parentModuleId() != null){
            parent = moduleRepository.findById(createModuleDTO.parentModuleId())
                .orElseThrow(() -> new ModuleDoesNotExistException(createModuleDTO.parentModuleId()));
        }
        return moduleMapper.toDTO(moduleRepository.save(moduleMapper.toModule(createModuleDTO, parent)));
    }
}
