package com.switchfully.lmstrapeziumbackend.module;


import com.switchfully.lmstrapeziumbackend.module.dto.ModuleDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
