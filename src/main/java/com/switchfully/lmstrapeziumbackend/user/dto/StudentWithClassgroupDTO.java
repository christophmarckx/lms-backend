package com.switchfully.lmstrapeziumbackend.user.dto;

import com.switchfully.lmstrapeziumbackend.classgroup.Classgroup;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.ClassgroupDTO;

import java.util.UUID;

public record StudentWithClassgroupDTO(
        UUID id,
        String email,
        String displayName,
        ClassgroupDTO classgroup
) {}
