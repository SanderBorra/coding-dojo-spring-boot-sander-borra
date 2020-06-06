package com.assignment.spring.exceptions;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@ResponseStatus(INTERNAL_SERVER_ERROR)
@Slf4j
public class SystemErrorException extends ApplicationException{

    public SystemErrorException(final Throwable e) {
        super(e);
        log.error("PROBLEEM!!!! ", e);
        
    }

    public SystemErrorException(final String error) {
        super(error);
        log.error(error);
    }
    

}
