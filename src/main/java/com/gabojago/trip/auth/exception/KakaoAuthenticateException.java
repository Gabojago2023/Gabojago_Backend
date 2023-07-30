package com.gabojago.trip.auth.exception;


import com.gabojago.trip.common.exception.AuthenticationException;

public class KakaoAuthenticateException extends AuthenticationException {

    public KakaoAuthenticateException(String reason) {
        super(reason);
    }
}
