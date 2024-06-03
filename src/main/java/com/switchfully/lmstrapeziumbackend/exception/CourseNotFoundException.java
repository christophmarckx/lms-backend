package com.switchfully.lmstrapeziumbackend.exception;

import org.springframework.http.HttpStatus;

public class CourseNotFoundException extends LMSException {
    public CourseNotFoundException() { super("No course with the provided id exists", HttpStatus.NOT_FOUND.value()); }
}
