package com.felipe.gestaoativosti.handler;

import com.felipe.gestaoativosti.exception.CustomNotFoundException;
import com.felipe.gestaoativosti.exception.CustomPatrimonioAlreadyExistsException;
import com.felipe.gestaoativosti.exception.CustomUsernameAlreadyExistsException;
import com.felipe.gestaoativosti.exception.CustomUnauthorizedResponse;
import com.felipe.gestaoativosti.exception.CustomValidationErrorResponse;
import com.felipe.gestaoativosti.exception.NotFoundException;
import com.felipe.gestaoativosti.exception.PatrimonioAlreadyExistsException;
import com.felipe.gestaoativosti.exception.UsernameAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CustomNotFoundException> handleNotFoundException(NotFoundException ex,
                                                                           HttpServletRequest request) {
        CustomNotFoundException response = new CustomNotFoundException(HttpStatus.NOT_FOUND.value(),
                ex.getMessage(), LocalDateTime.now(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(PatrimonioAlreadyExistsException.class)
    public ResponseEntity<CustomPatrimonioAlreadyExistsException> handlePatrimonioAlreadyExistsException(
            PatrimonioAlreadyExistsException ex,
            HttpServletRequest request) {

        CustomPatrimonioAlreadyExistsException response = new CustomPatrimonioAlreadyExistsException(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(), LocalDateTime.now(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<CustomUsernameAlreadyExistsException> handleUsernameAlreadyExistsException(
            UsernameAlreadyExistsException ex,
            HttpServletRequest request) {

        CustomUsernameAlreadyExistsException response = new CustomUsernameAlreadyExistsException(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(), LocalDateTime.now(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CustomUnauthorizedResponse> handleBadCredentialsException(
            BadCredentialsException ex,
            HttpServletRequest request) {

        CustomUnauthorizedResponse response = new CustomUnauthorizedResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Credenciais inválidas",
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        CustomValidationErrorResponse response = new CustomValidationErrorResponse(errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}

