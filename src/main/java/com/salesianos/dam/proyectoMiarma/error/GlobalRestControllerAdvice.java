package com.salesianos.dam.proyectoMiarma.error;

import com.salesianos.dam.proyectoMiarma.error.exception.EntityNotFoundException;
import com.salesianos.dam.proyectoMiarma.error.exception.UnauthoricedUserException;
import com.salesianos.dam.proyectoMiarma.error.model.ApiError;
import com.salesianos.dam.proyectoMiarma.error.model.ApiSubError;
import com.salesianos.dam.proyectoMiarma.error.model.ApiValidationSubError;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalRestControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(EntityNotFoundException ex, WebRequest request) {
        return buildApiError404(ex, request);
    }

    @ExceptionHandler({UnauthoricedUserException.class})
    public ResponseEntity<?> handleUnauthorizedUserException(UnauthoricedUserException ex, WebRequest request) {
        return buildApiError401(ex, request);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {


        List<ApiSubError> subErrorList = new ArrayList<>();

        ex.getAllErrors().forEach(error -> {

            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;

                subErrorList.add(
                        ApiValidationSubError.builder()
                                .object(fieldError.getObjectName())
                                .form(fieldError.getField())
                                .failValue(fieldError.getRejectedValue())
                                .message(fieldError.getDefaultMessage())
                                .build()
                );
            } else
            {
                ObjectError objectError = (ObjectError) error;

                subErrorList.add(
                        ApiValidationSubError.builder()
                                .object(objectError.getObjectName())
                                .message(objectError.getDefaultMessage())
                                .build()
                );
            }
        });

        return buildApiErrorWithSubError(HttpStatus.BAD_REQUEST, "Errores varios en la validación",
                request,
                subErrorList.isEmpty() ? null : subErrorList
        );
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildApiErrorStatus(status, ex, request);
    }

    private ResponseEntity<Object> buildApiError401(Exception ex, WebRequest request) {
        return buildApiErrorStatus(HttpStatus.UNAUTHORIZED, ex, request);
    }

    private ResponseEntity<Object> buildApiError404(Exception ex, WebRequest request) {
        return buildApiErrorStatus(HttpStatus.NOT_FOUND, ex, request);
    }

    private ResponseEntity<Object> buildApiErrorStatus(HttpStatus status, Exception ex, WebRequest request) {
        return ResponseEntity
                .status(status)
                .body(new ApiError(status, ex.getMessage(), ((ServletWebRequest) request).getRequest().getRequestURI()));
    }

    private ResponseEntity<Object> buildApiErrorWithSubError(HttpStatus estado, String mensaje, WebRequest request, List<ApiSubError> subErrores) {
        return ResponseEntity
                .status(estado)
                .body(new ApiError(estado, mensaje, ((ServletWebRequest) request).getRequest().getRequestURI(), subErrores));

    }
}
