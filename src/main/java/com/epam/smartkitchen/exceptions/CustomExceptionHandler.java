package com.epam.smartkitchen.exceptions;


import java.time.LocalDateTime;
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
        List<String> stringList = new LinkedList<>();
        String simpleName = ex.getClass().getSimpleName();
        String errorStatus = status.getReasonPhrase();
        String message = ex.getCause().getMessage();
        String[] split = message.split("\n");
        stringList.add(split[0]);
        ErrorResponse errorResponse = new ErrorResponse("400", errorStatus, "Request body is not correct",
                stringList);
        return ResponseEntity.badRequest().body(new Response<>(errorResponse, null, simpleName));
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Response<ErrorResponse, ?>> handleAllExceptions(Exception ex) {
        List<String> errorList = new LinkedList<>();
        errorList.add(ex.getLocalizedMessage());
        errorList.add(ex.getCause().getMessage());
        ErrorResponse error = new ErrorResponse("500", "ServerException", "Something wrong with server",
                errorList);
        Response<ErrorResponse, ?> response = new Response<>(error, null, ex.getClass().getSimpleName());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Response<ErrorResponse, ?>> handleUserNotFoundException(RecordNotFoundException ex) {
        List<String> errorList = new LinkedList<>();
        errorList.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("404", "NOT_FOUND", "Record not found",
                errorList);
        Response<ErrorResponse, ?> response = new Response<>(error, null, ex.getClass().getSimpleName());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RequestParamInvalidException.class)
    public final ResponseEntity<Response<ErrorResponse, ?>> handleParameterInvalidException(RequestParamInvalidException ex) {
        List<String> errorList = new LinkedList<>();
        errorList.add(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse("400", "BAD_REQUEST", "Parameter is not correct",
                errorList);
        Response<ErrorResponse, ?> response = new Response<>(errorResponse, null, ex.getClass().getSimpleName());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    public final ResponseEntity<Response<ErrorResponse, ?>> handleConflictException(ConflictException ex){
        List<String> errorList = new LinkedList<>();
        errorList.add(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse("409", "CONFLICT", "There is conflict this request",
                errorList);
        Response<ErrorResponse, ?> response = new Response<>(errorResponse, null, ex.getClass().getSimpleName());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(JwtExpiredException.class)
    public final ResponseEntity<Response<ErrorResponse, ?>> handleJwtExpiredException(JwtExpiredException ex){
        List<String> errorList = new LinkedList<>();
        errorList.add(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse("403", "FORBIDDEN",
                "You have problem with your jwt", errorList);
        Response<ErrorResponse, ?> response = new Response<>(errorResponse, null, ex.getClass().getSimpleName());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExpiredException.class)
    public final ResponseEntity<Response<ErrorResponse, ?>> handleExpiredException(ExpiredException ex){
        List<String> errorList = new LinkedList<>();
        errorList.add(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse("410", "GONE",
                "expired time passed", errorList);
        Response<ErrorResponse, ?> response = new Response<>(errorResponse,null,ex.getClass().getSimpleName());
        return new ResponseEntity<>(response,HttpStatus.GONE);
    }
}