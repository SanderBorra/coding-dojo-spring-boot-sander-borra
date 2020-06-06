package com.assignment.spring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
@Slf4j
public class UserException extends ApplicationException {
    public UserException(final String message) {
        super(message);
        log.info(message);
    }
}
