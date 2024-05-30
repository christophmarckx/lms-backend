package com.switchfully.lmstrapeziumbackend.exception;

public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException(){super("No student with the provided id exists");}
}
