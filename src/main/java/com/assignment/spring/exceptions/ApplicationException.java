package com.assignment.spring.exceptions;

public abstract class ApplicationException extends RuntimeException {
    public ApplicationException() {
    }

    public ApplicationException(final String exceptionString) {
        super(exceptionString);
    }
    
    public ApplicationException(final Throwable throwable) {
        super(throwable);

    }

}
