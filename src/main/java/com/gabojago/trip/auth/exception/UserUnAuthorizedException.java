package com.gabojago.trip.auth.exception;


import com.gabojago.trip.common.exception.UnauthorizedException;

public class UserUnAuthorizedException extends UnauthorizedException {

    public UserUnAuthorizedException(String reason) {
        super(reason);
    }
}
