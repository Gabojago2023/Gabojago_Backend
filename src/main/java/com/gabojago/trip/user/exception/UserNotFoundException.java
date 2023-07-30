package com.gabojago.trip.user.exception;


import com.gabojago.trip.common.exception.NotFoundException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND,reason);
    }
}
