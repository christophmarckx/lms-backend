package com.switchfully.lmstrapeziumbackend.user;

import com.switchfully.lmstrapeziumbackend.classgroup.ClassgroupService;
import com.switchfully.lmstrapeziumbackend.classgroup.dto.ClassgroupDTO;
import com.switchfully.lmstrapeziumbackend.user.dto.AuthenticatedUserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final ClassgroupService classgroupService;

    public UserController(UserService userService, ClassgroupService classgroupService) {
        this.userService = userService;
        this.classgroupService = classgroupService;
    }

    @GetMapping(produces = "application/json", path = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticatedUserDTO getAuthenticatedUser(@PathVariable UUID userId, Authentication authentication) {
        return this.userService.getAuthenticatedUser(userId, authentication);
    }

    @GetMapping(produces = "application/json", path = "/{userId}/classgroups")
    @ResponseStatus(HttpStatus.OK)
    public List<ClassgroupDTO> getAllClassgroupsForUserId(@PathVariable UUID userId, Authentication authentication) {
        return this.classgroupService.getAllClassgroupsForUserId(userId, authentication);
    }
}
