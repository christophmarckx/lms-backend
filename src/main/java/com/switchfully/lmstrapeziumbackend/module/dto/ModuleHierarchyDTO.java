package com.switchfully.lmstrapeziumbackend.module.dto;

import java.util.List;
import java.util.UUID;

public record ModuleHierarchyDTO(UUID id,
                                 String name,
                                 List<ModuleHierarchyDTO> modules) {
}
