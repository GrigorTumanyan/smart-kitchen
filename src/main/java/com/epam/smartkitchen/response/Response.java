package com.epam.smartkitchen.response;

public class Response<T, V> {

    private final T exception;
    private final V successBody;
    private final String responseBodyClassName;

    public Response(T exception, V successBody, String responseBodyClassName) {
        this.exception = exception;
        this.successBody = successBody;
        this.responseBodyClassName = responseBodyClassName;
    }

    public T getException() {
        return exception;
    }

    public V getSuccessObject() {
        return successBody;
    }

    public String getResponseBodyClassName() {
        return responseBodyClassName;
    }
}
