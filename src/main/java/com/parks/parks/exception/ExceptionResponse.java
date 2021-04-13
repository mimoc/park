package com.parks.parks.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ExceptionResponse {
    private List<String> errorMessage;
    private HttpStatus status;

    public ExceptionResponse( List<String>  errorMessage,HttpStatus statusCode) {
        this.errorMessage = errorMessage;
        this.status = statusCode;
    }

    public List<String>  getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(List<String>  errorMessage) {
        this.errorMessage = errorMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
