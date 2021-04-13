package com.parks.parks.exception.handler;

import com.parks.parks.exception.ExceptionResponse;
import com.parks.parks.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler
{
    private static final Logger logger = LoggerFactory.getLogger(CustomizedResponseEntityExceptionHandler.class);

    @ExceptionHandler({NotFoundException.class})
    public final ResponseEntity<Object> handleAllExceptions(NotFoundException ex)
    {

        ExceptionResponse exceptionResponse= new ExceptionResponse(Collections.singletonList(ex.getMessage()), HttpStatus.NOT_FOUND);
        logger.error(" Exception occurred " + ex.getMessage());

        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        List errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().stream().forEach(i->errors.add(i.getField() + " : "+i.getDefaultMessage()));
        ExceptionResponse exceptionResponse= new ExceptionResponse( errors, HttpStatus.BAD_REQUEST);
        logger.error(" Exception occurred " + ex.getMessage());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { ConstraintViolationException.class })
    public ResponseEntity<Object> handleResourceNotFoundException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : violations ) {
            strBuilder.append(violation.getMessage() + "\n");
        }
        return new ResponseEntity<>(strBuilder, HttpStatus.BAD_REQUEST);
    }

}
