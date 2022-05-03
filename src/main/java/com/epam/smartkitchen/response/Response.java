package com.epam.smartkitchen.response;

import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.models.User;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class Response<T, V> {

    private final T exception;
    private final V successObject;
    private final String responseBodyClassName;


    public Response(T exception, V success, String responseBodyClassName) {
        this.exception = exception;
        this.successObject = success;
        this.responseBodyClassName = responseBodyClassName;
    }

    public T getException() {
        return exception;
    }

    public V getSuccessObject() {
        return successObject;
    }

    public String getResponseBodyClassName() {
        return responseBodyClassName;
    }
}
