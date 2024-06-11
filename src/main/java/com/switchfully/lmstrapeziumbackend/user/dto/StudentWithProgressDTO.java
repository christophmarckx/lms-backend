package com.switchfully.lmstrapeziumbackend.user.dto;

import com.switchfully.lmstrapeziumbackend.user.dto.StudentDTO;

import java.util.List;

public record StudentWithProgressDTO (
        StudentWithClassgroupDTO student,
        int actualProgression,
        int totalProgression
) {
}
