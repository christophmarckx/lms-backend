package com.switchfully.lmstrapeziumbackend.user;

import com.switchfully.lmstrapeziumbackend.user.dto.AuthenticatedUserDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public AuthenticatedUserDTO getAuthenticatedUser() {
        return this.userService.getAuthenticatedUser();
    }
}
