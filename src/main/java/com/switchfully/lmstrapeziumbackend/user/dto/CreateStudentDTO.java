package com.switchfully.lmstrapeziumbackend.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record CreateStudentDTO(
        @NotEmpty(message = EMAIL_REQUIRED_MESSAGE)
        @Email(message = EMAIL_FORMAT_MESSAGE)
        String email,
        @NotEmpty(message = PASSWORD_REQUIRED_MESSAGE)
        String password,
        @NotEmpty(message = DISPLAY_NAME_REQUIRED_MESSAGE)
        String displayName
) {
    public static final String EMAIL_REQUIRED_MESSAGE = "Email is required";
    public static final String EMAIL_FORMAT_MESSAGE = "Email format must be x@x.x";
    public static final String DISPLAY_NAME_REQUIRED_MESSAGE = "Display name is required";
    public static final String PASSWORD_REQUIRED_MESSAGE = "Password is required";
}
