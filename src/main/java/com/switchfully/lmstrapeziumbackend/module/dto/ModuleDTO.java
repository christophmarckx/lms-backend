package com.switchfully.lmstrapeziumbackend.module.dto;

import java.util.UUID;

public record ModuleDTO(
     UUID id,
     String name,
     ModuleDTO parentModule
) {
}
