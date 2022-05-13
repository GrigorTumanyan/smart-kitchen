package com.epam.smartkitchen.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequestParamInvalidException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public RequestParamInvalidException(String message) {
        super(message);
    }
}