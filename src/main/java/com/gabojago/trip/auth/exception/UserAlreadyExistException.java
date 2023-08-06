package com.gabojago.trip.auth.exception;


import com.gabojago.trip.common.exception.BadRequestException;

public class UserAlreadyExistException extends BadRequestException {

    public UserAlreadyExistException(String reason) {
        super(reason);
    }
}
