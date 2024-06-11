package com.switchfully.lmstrapeziumbackend.codelab;

import com.switchfully.lmstrapeziumbackend.codelab.dto.*;
import com.switchfully.lmstrapeziumbackend.progress.CodelabProgress;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/codelabs")
public class CodelabController {
    private final CodelabService codelabService;

    public CodelabController(CodelabService codelabService) {
        this.codelabService = codelabService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CodelabDTO> getAll() {
        return codelabService.getAllCodelabs();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CodelabWithModuleDTO getById(@PathVariable UUID id) {
        return codelabService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CodelabDTO createCodelab(@RequestBody @Valid CreateCodelabDTO codelabDTO) {
        return codelabService.createCodelab(codelabDTO);
    }

    @PutMapping("/{id}")
    public CodelabDTO updateById(@RequestBody UpdateCodelabDTO updateCodelabDTO, @PathVariable UUID id) {
        return codelabService.updateCodelab(id, updateCodelabDTO);
    }

    @PutMapping("{codelabId}/progress")
    public CodelabDTO updateCodelabProgress(@PathVariable UUID codelabId, @RequestBody CodelabProgress codelabProgress, Authentication authentication) {
        return codelabService.updateCodelabProgress(codelabId, codelabProgress, authentication);
    }

    @PostMapping("/{codelabId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDTO createCodelabComment(@PathVariable UUID codelabId, @RequestBody CreateCommentDTO codelabCommentDTO, Authentication authentication) {
        return codelabService.createCodelabComment(codelabId, codelabCommentDTO, authentication);
    }

    @GetMapping("/{codelabId}/comments")
    public CodelabWithCommentsDTO getCodelabWithCommentsById(@PathVariable UUID codelabId){
        return codelabService.getCodelabWithCommentsById(codelabId);
    }
}
