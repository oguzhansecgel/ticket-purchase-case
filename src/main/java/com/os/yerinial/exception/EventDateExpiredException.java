package com.os.yerinial.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EventDateExpiredException extends BusinessException{
    public EventDateExpiredException(String message) {
        super(message);
    }
}
