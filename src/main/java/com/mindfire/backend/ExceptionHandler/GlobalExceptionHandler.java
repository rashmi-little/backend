package com.mindfire.backend.ExceptionHandler;

import com.mindfire.backend.customException.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Handles validation exceptions when arguments are not valid (e.g., @Valid validation errors)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleInvalidArguements(MethodArgumentNotValidException ex){
        Map<String, String> map=new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error->{
            map.put(error.getField(), error.getDefaultMessage());
        });
        return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link UserNotFoundException} and returns a {@link ProblemDetail} with HTTP 404 status
     * and the exception message as the detail.
     *
     * @param ex the {@link UserNotFoundException} to handle
     * @return a {@link ProblemDetail} with status 404 and the exception message
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleIfUserNotFound(UserNotFoundException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(404),ex.getMessage());
    }
    /**
     * Handles general exceptions thrown in the application.
     * <p>
     * This method catches all exceptions of type {@link Exception} and returns a {@link ProblemDetail}
     * with a status code of 500 (Internal Server Error) and the exception message as the detail.
     *
     * @param exception the exception that was thrown
     * @return a {@link ProblemDetail} representing the error response with HTTP status 500
     */
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(500), exception.getMessage());
    }
}
