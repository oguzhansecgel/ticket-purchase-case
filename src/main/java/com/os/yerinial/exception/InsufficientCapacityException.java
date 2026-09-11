package com.os.yerinial.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INSUFFICIENT_STORAGE)
public class InsufficientCapacityException extends BusinessException{
    public InsufficientCapacityException(String message) {
        super(message);
    }
}
