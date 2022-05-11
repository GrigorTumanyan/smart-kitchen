package com.epam.smartkitchen.exceptions;


import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import com.epam.smartkitchen.response.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        String message = ex.getMessage();
        String message1 = ex.getCause().getMessage();
        InputStream inputStream = null;
        StackTraceElement[] stackTrace = ex.getRootCause().getStackTrace();
        try {
            inputStream = ex.getHttpInputMessage().getBody();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String simpleName = ex.getClass().getSimpleName();
        String reasonPhrase = status.getReasonPhrase();
        List<Object> stringList = new LinkedList<>();
        stringList.add("Request is not correct");
        stringList.add(inputStream);
        ErrorResponse errorResponse = new ErrorResponse("400", reasonPhrase, message, null);
        Response response = new Response(errorResponse, null,simpleName);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Response<ErrorResponse, ?>> handleAllExceptions(Exception ex) {
        List<String> errorList = new ArrayList<>();
        errorList.add(ex.getLocalizedMessage());
        errorList.add(ex.getCause().getMessage());
        ErrorResponse error = new ErrorResponse("500", "ServerException", "Something wrong with server",
                errorList);
        Response<ErrorResponse, ?> response = new Response<>(error, null, ex.getClass().getSimpleName());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Response<ErrorResponse, ?>> handleUserNotFoundException(RecordNotFoundException ex) {
        List<String> errorList = new ArrayList<>();
        errorList.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("404", "NOT_FOUND", "Record not found", errorList);
        Response<ErrorResponse, ?> response = new Response<>(error, null, ex.getClass().getSimpleName());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RequestParamInvalidException.class)
    public final ResponseEntity<Response<ErrorResponse, ?>> handleParameterInvalidException(RequestParamInvalidException ex) {
        List<String> errorList = new ArrayList<>();
        errorList.add(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse("400", "BAD_REQUEST", "Parameter is not correct",
                errorList);
        Response<ErrorResponse, ?> response = new Response<>(errorResponse, null, ex.getClass().getSimpleName());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateException.class)
    public final ResponseEntity<Response<ErrorResponse, ?>> handleResourceExistException(DuplicateException ex) {
        List<String> errorList = new ArrayList<>();
        errorList.add(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse("409", "CONFLICT", "Resource already exists",
                errorList);
        Response<ErrorResponse, ?> response = new Response<>(errorResponse, null, ex.getClass().getSimpleName());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}