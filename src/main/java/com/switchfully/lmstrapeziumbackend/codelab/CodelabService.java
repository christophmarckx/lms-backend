package com.switchfully.lmstrapeziumbackend.codelab;

import com.switchfully.lmstrapeziumbackend.codelab.dto.CodelabDTO;
import com.switchfully.lmstrapeziumbackend.codelab.dto.CreateCodelabDTO;
import com.switchfully.lmstrapeziumbackend.exception.CodelabNotFoundException;
import com.switchfully.lmstrapeziumbackend.module.Module;
import com.switchfully.lmstrapeziumbackend.module.ModuleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CodelabService {
    private final CodelabRepository codelabRepository;
    private final ModuleService moduleService;

    public CodelabService(CodelabRepository codelabRepository, ModuleService moduleService) {
        this.codelabRepository = codelabRepository;
        this.moduleService = moduleService;
    }

    public CodelabDTO createCodelab(CreateCodelabDTO codelabDTO) {
        Module codelabParentModule = moduleService.getModuleById(codelabDTO.parentModuleId());
        Codelab codelabToSave = CodelabMapper.toCodelab(codelabDTO, codelabParentModule);
        Codelab savedCodelab = codelabRepository.save(codelabToSave);
        return CodelabMapper.toDTO(savedCodelab);
    }

    public List<CodelabDTO> getAllCodelabs() {
        return CodelabMapper.toDTO(codelabRepository.findAll());
    }

    public CodelabDTO getById(UUID id) {
        return CodelabMapper.toDTO(codelabRepository.findById(id).orElseThrow(() -> new CodelabNotFoundException(id)));
    }
}
