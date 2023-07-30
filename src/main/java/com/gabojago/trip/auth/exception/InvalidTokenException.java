package com.gabojago.trip.auth.exception;


import com.gabojago.trip.common.exception.ForbiddenException;

public class InvalidTokenException extends ForbiddenException {
    public InvalidTokenException(String reason) {
        super(reason);
    }
}
