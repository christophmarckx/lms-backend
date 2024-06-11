package com.switchfully.lmstrapeziumbackend.codelab.dto;

import com.switchfully.lmstrapeziumbackend.user.dto.StudentDTO;

import java.util.UUID;

public record CommentDTO(UUID id, UUID codelabId, String comment, StudentDTO student) { }
