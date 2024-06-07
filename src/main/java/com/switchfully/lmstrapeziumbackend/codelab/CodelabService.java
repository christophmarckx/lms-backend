package com.switchfully.lmstrapeziumbackend.codelab;

import com.switchfully.lmstrapeziumbackend.codelab.dto.CodelabDTO;
import com.switchfully.lmstrapeziumbackend.codelab.dto.CodelabWithModuleDTO;
import com.switchfully.lmstrapeziumbackend.codelab.dto.CreateCodelabDTO;
import com.switchfully.lmstrapeziumbackend.codelab.dto.UpdateCodelabDTO;
import com.switchfully.lmstrapeziumbackend.course.Course;
import com.switchfully.lmstrapeziumbackend.course.CourseMapper;
import com.switchfully.lmstrapeziumbackend.exception.CodelabNotFoundException;
import com.switchfully.lmstrapeziumbackend.module.Module;
import com.switchfully.lmstrapeziumbackend.module.ModuleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public List<Codelab> getCodelabsByModuleId(UUID moduleId) {
        return codelabRepository.findCodelabsByModuleId(moduleId);
    }

    public List<CodelabDTO> getAllCodelabs() {
        return CodelabMapper.toDTO(codelabRepository.findAll());
    }

    public CodelabWithModuleDTO getById(UUID id) {
        return CodelabMapper.toCodelabWithModuleDTO(codelabRepository.findById(id).orElseThrow(() -> new CodelabNotFoundException(id)));
    }

    public CodelabDTO updateCodelab(UUID codelabId, UpdateCodelabDTO updateCodelabDTO) {
        Codelab codelabFound = getCodelabById(codelabId);
        codelabFound.updateCodelabName(updateCodelabDTO.name());
        codelabFound.updateCodelabDescription(updateCodelabDTO.description());
        if (!codelabFound.getModule().getId().equals(updateCodelabDTO.moduleId())) {
            codelabFound.updateCodelabModule(moduleService.getModuleById(updateCodelabDTO.moduleId()));
        }
        return CodelabMapper.toDTO(codelabFound);
    }

    public Codelab getCodelabById(UUID codelabId) {
        Optional<Codelab> codelabOptional = codelabRepository.findById(codelabId);
        if (codelabOptional.isEmpty()) {
            throw new CodelabNotFoundException(codelabId);
        }
        return codelabOptional.get();
    }
}
