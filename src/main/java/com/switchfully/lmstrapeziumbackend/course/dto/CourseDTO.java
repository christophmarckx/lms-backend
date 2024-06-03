package com.switchfully.lmstrapeziumbackend.course.dto;

import com.switchfully.lmstrapeziumbackend.module.dto.ModuleDTO;

import java.util.List;
import java.util.UUID;

public record CourseDTO(    UUID id,
                            String name,
                            String description,
                            List<ModuleDTO> modules
) {}
