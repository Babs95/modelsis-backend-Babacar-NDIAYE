package com.modelsis.backend.Babacar.NDIAYE.test_app.config;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.response.common.ErrorResponse;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.response.common.FieldError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
@RestControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(final ResponseStatusException exception) {
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .httpStatus(exception.getStatus().value())
                .exception(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .fieldErrors(Collections.emptyList())
                .build();
        return new ResponseEntity<>(errorResponse, exception.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException exception) {
        final BindingResult bindingResult = exception.getBindingResult();
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors()
                .stream()
                .map(error -> FieldError.builder()
                        .errorCode(error.getCode())
                        .field(error.getField())
                        .build())
                .collect(Collectors.toList());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .exception(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .fieldErrors(fieldErrors)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleThrowable(final Throwable exception) {
        exception.printStackTrace();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .exception(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .fieldErrors(Collections.emptyList())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
