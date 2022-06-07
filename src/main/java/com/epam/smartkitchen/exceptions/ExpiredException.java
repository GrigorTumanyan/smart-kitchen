package com.epam.smartkitchen.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.GONE)
public class ExpiredException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ExpiredException(String exception) {
        super(exception);
    }
}
