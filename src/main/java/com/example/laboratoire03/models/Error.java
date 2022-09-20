package com.example.laboratoire03.models;

public class Error {

    public Error(String url, Exception exception) {
        this.url = url;
        this.exception = exception;
    }
    private String url;
    private Exception exception;
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public Exception getException() {
        return exception;
    }
    public void setException(Exception exception) {
        this.exception = exception;
    }
}
