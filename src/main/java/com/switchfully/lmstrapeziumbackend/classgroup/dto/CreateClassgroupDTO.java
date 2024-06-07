package com.switchfully.lmstrapeziumbackend.classgroup.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record CreateClassgroupDTO(
        @NotNull(message = "The name of the classgroup must be between 2 and 255 characters")
        @Size(min = 2, max = 255, message = "The name of the classgroup must be between 2 and 255 characters")
        String name,

        @NotNull(message = "The id of the course must be 36 characters long")
        UUID courseId,

        @NotNull(message = "Provide at least one coach to create the Classgroup")
        @Size(min = 1, message = "Provide at least one coach to create the Classgroup")
        List<UUID> coaches
) {
}
