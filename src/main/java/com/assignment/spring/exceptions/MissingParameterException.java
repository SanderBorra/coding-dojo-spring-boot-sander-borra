package com.assignment.spring.exceptions;

import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@ResponseStatus(METHOD_NOT_ALLOWED)
@Slf4j
public class MissingParameterException extends ApplicationException {

    public MissingParameterException(final String exceptionString) {
        super(exceptionString);
        log.info(exceptionString);
    }

}
