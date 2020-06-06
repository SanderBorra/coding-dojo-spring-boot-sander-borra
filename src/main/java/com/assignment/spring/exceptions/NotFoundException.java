package com.assignment.spring.exceptions;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@ResponseStatus(NOT_FOUND)
@Slf4j
public class NotFoundException extends ApplicationException {
    public NotFoundException(final String message) {
        super(message);
        log.info(message);
    }
}
