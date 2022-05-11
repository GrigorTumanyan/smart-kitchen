package com.epam.smartkitchen.exceptions;

public class ResourceExistException extends RuntimeException{
    public ResourceExistException(String message) {
        super(message);
    }
}
