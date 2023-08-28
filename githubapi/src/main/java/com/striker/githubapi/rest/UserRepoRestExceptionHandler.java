package com.striker.githubapi.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class UserRepoRestExceptionHandler {


    @ExceptionHandler
    public ResponseEntity<UserRepoErrorResponse> handleException(WrongHeaderException exc){

        UserRepoErrorResponse userRepoErrorResponse = new UserRepoErrorResponse();

        userRepoErrorResponse.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        userRepoErrorResponse.setMessage(exc.getMessage());

        return new ResponseEntity<>(userRepoErrorResponse, HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler
    public ResponseEntity<UserRepoErrorResponse> handleException(UserNotFoundException exc){

        UserRepoErrorResponse userRepoErrorResponse = new UserRepoErrorResponse();

        userRepoErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        userRepoErrorResponse.setMessage(exc.getMessage());

        return new ResponseEntity<>(userRepoErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<UserRepoErrorResponse> handleException(Exception exc){


        UserRepoErrorResponse error = new UserRepoErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exc.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
