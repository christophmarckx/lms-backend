package com.switchfully.lmstrapeziumbackend.codelab.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.switchfully.lmstrapeziumbackend.progress.CodelabProgress;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CodelabDTO(
        UUID id,
        String name,
        String description,
        CodelabProgress progress
) {
}
