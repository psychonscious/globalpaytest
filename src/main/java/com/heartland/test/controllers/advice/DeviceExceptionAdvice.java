package com.heartland.test.controllers.advice;

import com.heartland.test.enums.ERRS;
import com.heartland.test.exceptions.DeviceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class DeviceExceptionAdvice extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceExceptionAdvice.class);

    protected ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                                           HttpHeaders headers,
                                                                           HttpStatus status,
                                                                           WebRequest req) {
        LOG.info(e.getMessage());
        List<ERRS> errors = new ArrayList<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errors.add(ERRS.valueOf(fieldError.getDefaultMessage()));
        }

        return new ResponseEntity<>(errors, HttpStatus.OK);
    }

    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e,
                                                                        HttpHeaders headers,
                                                                        HttpStatus status,
                                                                        WebRequest req) {
        LOG.info(e.getMessage());
        List<ERRS> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            errors.add(ERRS.valueOf(violation.getMessage()));
        }

        return new ResponseEntity<>(errors, HttpStatus.OK);
    }

    protected ResponseEntity<Object> handleDeviceException(DeviceException e,
                                                           HttpHeaders headers,
                                                           HttpStatus status,
                                                           WebRequest req) {
        LOG.info(e.getMessage());
        List<ERRS> errors = e.getErrors();

        return new ResponseEntity<>(errors, HttpStatus.OK);
    }
}
