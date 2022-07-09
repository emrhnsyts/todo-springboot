package com.emrhnsyts.todo.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
        validationErrorResponse.setMessage("Validation failed");
        validationErrorResponse.setCreatedAt(new Date());
        validationErrorResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        validationErrorResponse.setDetails(ex.getBindingResult().getAllErrors().stream().map(objectError -> {
            return objectError.getDefaultMessage();
        }).collect(Collectors.toList()));

        return new ResponseEntity<>(validationErrorResponse, validationErrorResponse.getHttpStatus());
    }

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<Object> handleTodoNotFoundException(Exception exception) {
        ErrorResponse errorResponse =
                ErrorResponse.builder().message(exception.getMessage())
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .createdAt(new Date()).build();

        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }
}
