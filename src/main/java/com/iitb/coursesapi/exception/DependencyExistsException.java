package com.iitb.coursesapi.exception;

public class DependencyExistsException extends RuntimeException {
    public DependencyExistsException(String message) {
        super(message);
    }
}
