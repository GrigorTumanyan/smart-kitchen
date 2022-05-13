package com.epam.smartkitchen.exceptions;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {


    private String errorCode;
    private String errorStatus;
    private String errorMessage;
    private List<String> errorList;
    private LocalDateTime localDateTime;


    public ErrorResponse() {
    }

    public ErrorResponse(String errorCode, String errorStatus, String errorMessage, List<String> errorList) {
        this.errorCode = errorCode;
        this.errorStatus = errorStatus;
        this.errorMessage = errorMessage;
        this.errorList = errorList;
        this.localDateTime = LocalDateTime.now();
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
    public List<String> getErrorList() {
        return errorList;
    }
    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }
    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
