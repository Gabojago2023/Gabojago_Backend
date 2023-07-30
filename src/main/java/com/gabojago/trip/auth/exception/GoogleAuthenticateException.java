package com.gabojago.trip.auth.exception;


import com.gabojago.trip.common.exception.AuthenticationException;

public class GoogleAuthenticateException extends AuthenticationException {

    public GoogleAuthenticateException(String reason) {
        super(reason);
    }
}
