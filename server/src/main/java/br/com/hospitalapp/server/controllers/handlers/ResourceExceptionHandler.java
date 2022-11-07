package br.com.hospitalapp.server.controllers.handlers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.hospitalapp.server.services.exceptions.BadRequestException;
import br.com.hospitalapp.server.services.exceptions.ConstraintViolationException;
import br.com.hospitalapp.server.services.exceptions.DataBaseException;
import br.com.hospitalapp.server.services.exceptions.InternalServerException;
import br.com.hospitalapp.server.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError StandardError = new StandardError();
        StandardError.setTimestamp(LocalDateTime.now());
        StandardError.setStatus(HttpStatus.NOT_FOUND.toString());
        StandardError.setFrom("hospital-app");
        StandardError.setMessage(e.getMessage());
        return ResponseEntity.status(status).body(StandardError);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<StandardError> internalServerError(InternalServerException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        StandardError StandardError = new StandardError();
        StandardError.setTimestamp(LocalDateTime.now());
        StandardError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        StandardError.setFrom("hospital-app");
        StandardError.setMessage(e.getMessage());
        return ResponseEntity.status(status).body(StandardError);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<StandardError> badRequest(BadRequestException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError StandardError = new StandardError();
        StandardError.setTimestamp(LocalDateTime.now());
        StandardError.setStatus(HttpStatus.BAD_REQUEST.toString());
        StandardError.setFrom("hospital-app");
        StandardError.setMessage(e.getMessage());
        return ResponseEntity.status(status).body(StandardError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandardError> constraintViolation(ConstraintViolationException e,
            HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        StandardError StandardError = new StandardError();
        StandardError.setTimestamp(LocalDateTime.now());
        StandardError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        StandardError.setFrom("hospital-app");
        StandardError.setMessage(e.getMessage());
        return ResponseEntity.status(status).body(StandardError);
    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<StandardError> dataBaseException(DataBaseException e,
            HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        StandardError StandardError = new StandardError();
        StandardError.setTimestamp(LocalDateTime.now());
        StandardError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        StandardError.setFrom("hospital-app");
        StandardError.setMessage(e.getMessage());
        return ResponseEntity.status(status).body(StandardError);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            errors.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss")));
            errors.put("status", HttpStatus.BAD_REQUEST.toString());
            errors.put("from", "hospital-app");
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<Object>(errors, status);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException e,
            HttpHeaders headers, HttpStatus status,
            WebRequest request) {

        StandardError errors = new StandardError();
        errors.setTimestamp(LocalDateTime.now());
        errors.setStatus(status.toString());
        errors.setFrom("hospital-app");
        errors.setMessage(e.getMessage());

        return ResponseEntity.status(status).body(errors);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
            HttpHeaders headers, HttpStatus status,
            WebRequest request) {

        StandardError errors = new StandardError();
        errors.setTimestamp(LocalDateTime.now());
        errors.setStatus(status.toString());
        errors.setFrom("hospital-app");
        errors.setMessage(e.getMessage());

        return ResponseEntity.status(status).body(errors);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
            HttpHeaders headers, HttpStatus status,
            WebRequest request) {

        StandardError errors = new StandardError();
        errors.setTimestamp(LocalDateTime.now());
        errors.setStatus(status.toString());
        errors.setFrom("hospital-app");
        errors.setMessage(e.getMessage());

        return ResponseEntity.status(status).body(errors);
    }
}
