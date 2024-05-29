package com.switchfully.lmstrapeziumbackend.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException() { super("No course with the provided id exists"); }
}
