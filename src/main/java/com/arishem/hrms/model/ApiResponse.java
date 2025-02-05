package com.arishem.hrms.model;

import java.util.List;

public class ApiResponse<T> {
    private int status;
    private String errorcode;
    private String message;
    private List<T> response;

    public ApiResponse(int status, String errorcode, String message, List<T> response) {
        this.status = status;
        this.errorcode = errorcode;
        this.message = message;
        this.response = response;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getResponse() {
        return response;
    }

    public void setResponse(List<T> response) {
        this.response = response;
    }
}