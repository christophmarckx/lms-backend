package com.switchfully.lmstrapeziumbackend.user;

import com.switchfully.lmstrapeziumbackend.user.dto.AuthenticatedUserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public AuthenticatedUserDTO getAuthenticatedUser(Authentication authentication) {
        return this.userService.getAuthenticatedUser(authentication);
    }
}
