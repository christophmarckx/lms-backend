package com.switchfully.lmstrapeziumbackend.course.dto;

import com.switchfully.lmstrapeziumbackend.module.dto.ModuleWithCodelabsDTO;

import java.util.List;
import java.util.UUID;

public record CourseWithModulesDTO(UUID id,
                                   String name,
                                   String description,
                                   List<ModuleWithCodelabsDTO> modules
) {
}
