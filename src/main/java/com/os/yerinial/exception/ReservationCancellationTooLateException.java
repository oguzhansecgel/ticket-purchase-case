package com.os.yerinial.exception;

public class ReservationCancellationTooLateException extends BusinessException{
    public ReservationCancellationTooLateException(String message) {
        super(message);
    }
}
