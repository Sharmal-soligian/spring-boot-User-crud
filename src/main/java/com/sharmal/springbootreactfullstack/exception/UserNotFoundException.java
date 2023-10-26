package com.sharmal.springbootreactfullstack.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(Long id) {
        super("Could not found user with this id " + id);
    }
}
