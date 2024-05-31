package com.switchfully.lmstrapeziumbackend.codelab;

import com.switchfully.lmstrapeziumbackend.codelab.dto.CodelabDTO;
import com.switchfully.lmstrapeziumbackend.codelab.dto.CreateCodelabDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}
