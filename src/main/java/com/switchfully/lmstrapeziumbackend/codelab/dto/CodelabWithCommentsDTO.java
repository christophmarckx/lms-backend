package com.switchfully.lmstrapeziumbackend.codelab.dto;
import com.switchfully.lmstrapeziumbackend.comment.Comment;
import com.switchfully.lmstrapeziumbackend.progress.CodelabProgress;
import java.util.List;
import java.util.UUID;

public record CodelabWithCommentsDTO(
        UUID id,
        String name,
        String description,
        List<CommentDTO> comments
){ }
