package com.striker.githubapi.rest;

public class WrongHeaderException extends RuntimeException {

    public WrongHeaderException(String message) {
        super(message);
    }

    public WrongHeaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongHeaderException(Throwable cause) {
        super(cause);
    }
}
