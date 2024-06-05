package com.switchfully.lmstrapeziumbackend.user.coach;

import com.switchfully.lmstrapeziumbackend.user.dto.CoachDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/coaches")
public class CoachController {
    private CoachService coachService;
    public CoachController(CoachService coachService) {
        this.coachService = coachService;
    }

    @GetMapping(produces= "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<CoachDTO> getAllCoaches(){
        return coachService.getAllCoaches();
    }
}
