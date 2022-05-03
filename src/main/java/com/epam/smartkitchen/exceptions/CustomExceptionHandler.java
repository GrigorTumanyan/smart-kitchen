package com.epam.smartkitchen.exceptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.epam.smartkitchen.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Response<ErrorResponse, ? >> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> errorList = new ArrayList<>();
        errorList.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("500", "ServerException", "Something wrong with server",
                errorList, LocalDateTime.now());
        Response<ErrorResponse, ?> response = new Response<>(error, null, ex.getCause().getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Response<ErrorResponse, ?>> handleUserNotFoundException(RecordNotFoundException ex, WebRequest request) {
        List<String> errorList = new ArrayList<>();
        errorList.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("404", "NOT_FOUND", "Record not found",
                errorList, LocalDateTime.now());
        Response<ErrorResponse, ?> response = new Response<>(error, null, RecordNotFoundException.class.getSimpleName());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ParamInvalidException.class)
    public final ResponseEntity<Response<ErrorResponse, ?>> handleParameterInvalidException (ParamInvalidException ex, WebRequest request){
        List<String> errorList = new ArrayList<>();
        errorList.add(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse("400", "BAD_REQUEST", "Parameter is not correct",
                errorList, LocalDateTime.now());
        Response<ErrorResponse, ?> response = new Response<>(errorResponse, null, ex.getClass().getSimpleName());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}