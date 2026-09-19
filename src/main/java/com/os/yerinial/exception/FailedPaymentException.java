package com.os.yerinial.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FailedPaymentException extends BusinessException{
    public FailedPaymentException(String message) {
        super(message);
    }
}
