package com.switchfully.lmstrapeziumbackend.codelab;

import com.switchfully.lmstrapeziumbackend.codelab.dto.CodelabDTO;
import com.switchfully.lmstrapeziumbackend.codelab.dto.CodelabWithCommentsDTO;
import com.switchfully.lmstrapeziumbackend.codelab.dto.CodelabWithModuleDTO;
import com.switchfully.lmstrapeziumbackend.codelab.dto.CreateCodelabDTO;
import com.switchfully.lmstrapeziumbackend.comment.Comment;
import com.switchfully.lmstrapeziumbackend.module.Module;
import com.switchfully.lmstrapeziumbackend.module.ModuleMapper;
import com.switchfully.lmstrapeziumbackend.progress.CodelabProgress;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

public class CodelabMapper {
    public static Codelab toCodelab(CreateCodelabDTO createCodelabDTO, Module codelabParentModule) {
        return new Codelab(
                createCodelabDTO.name(),
                createCodelabDTO.description(),
                codelabParentModule
        );
    }

    public static CodelabDTO toDTO(Codelab savedCodelab) {
        return new CodelabDTO(savedCodelab.getId(),
                savedCodelab.getName(),
                savedCodelab.getDescription(),
                null
        );
    }

    public static CodelabDTO toDTO(Codelab savedCodelab, CodelabProgress codelabProgress) {
        return new CodelabDTO(savedCodelab.getId(),
                savedCodelab.getName(),
                savedCodelab.getDescription(),
                codelabProgress
                );
    }

    public static CodelabWithModuleDTO toCodelabWithModuleDTO(Codelab savedCodelab) {
        return new CodelabWithModuleDTO(savedCodelab.getId(),
                savedCodelab.getName(),
                savedCodelab.getDescription(),
                ModuleMapper.toDTO(savedCodelab.getModule()));
    }

    public static List<CodelabDTO> toDTO(Collection<Codelab> codelabs) {
        return codelabs.stream().map(CodelabMapper::toDTO).toList();
    }

    public static List<CodelabDTO> toDTO(List<Codelab> codelabs, List<CodelabProgress> codelabProgresses) {
        return IntStream.range(0, codelabs.size())
                .mapToObj(i -> toDTO(codelabs.get(i), codelabProgresses.get(i)))
                .toList();
    }

    public static CodelabWithCommentsDTO toCodelabWithCommentsDTO(Codelab savedCodelab, List<Comment> comments) {
        return new CodelabWithCommentsDTO(savedCodelab.getId(),
                savedCodelab.getName(),
                savedCodelab.getDescription(),
                comments.stream().map(CommentMapper::toDTO).toList()
        );
    }
}
