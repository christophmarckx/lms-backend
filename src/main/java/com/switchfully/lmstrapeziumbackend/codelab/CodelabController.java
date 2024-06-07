package com.switchfully.lmstrapeziumbackend.codelab;

import com.switchfully.lmstrapeziumbackend.codelab.dto.CodelabDTO;
import com.switchfully.lmstrapeziumbackend.codelab.dto.CodelabWithModuleDTO;
import com.switchfully.lmstrapeziumbackend.codelab.dto.CreateCodelabDTO;
import com.switchfully.lmstrapeziumbackend.codelab.dto.UpdateCodelabDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CodelabDTO createCodelab(@RequestBody @Valid CreateCodelabDTO codelabDTO) {
        return codelabService.createCodelab(codelabDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CodelabDTO> getAll() {
        return codelabService.getAllCodelabs();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CodelabWithModuleDTO getById(@PathVariable UUID id){
        return codelabService.getById(id);
    }

    @PutMapping("/{id}")
    public CodelabDTO updateById(@RequestBody UpdateCodelabDTO updateCodelabDTO, @PathVariable UUID id) {
        return codelabService.updateCodelab(id, updateCodelabDTO);
    }
}
