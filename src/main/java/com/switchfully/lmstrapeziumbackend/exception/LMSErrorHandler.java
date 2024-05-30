package com.switchfully.lmstrapeziumbackend.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class LMSErrorHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        String defaultErrorMessage = "Following validation error(s) occurred on " + getRequestUri(request);
        response.put("message", defaultErrorMessage);

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        response.put("errors", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private String getRequestUri(WebRequest request) {
        String requestUri = request.getDescription(false);
        if (requestUri != null && requestUri.startsWith("uri=")) {
            requestUri = requestUri.substring(4);
        }
        return requestUri;
    }

    @ExceptionHandler({UserAlreadyExistException.class})
    public ResponseEntity<String> userAlreadyExistException(UserAlreadyExistException exception, HttpServletResponse response) throws IOException {
        this.logger.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(exception.getMessage());
    }

    @ExceptionHandler({StudentNotFoundException.class})
    public ResponseEntity<String> studentNotFoundException(StudentNotFoundException exception, HttpServletResponse response) throws IOException {
        this.logger.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(exception.getMessage());
    }
}
