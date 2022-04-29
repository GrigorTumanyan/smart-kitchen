package com.epam.smartkitchen.exceptions;

public class ErrorResponse {


    private String errorCode;
    private String errorStatus;
    private String errorMessage;


    public ErrorResponse() {
    }

    public ErrorResponse(String errorCode, String errorStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.errorStatus = errorStatus;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    public String getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    public String getErrorStatus() {
        return errorStatus;
    }
    public void setErrorStatus(String errorStatus) {
        this.errorStatus = errorStatus;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }



}
