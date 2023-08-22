package com.gabojago.trip.user.exception;

import com.gabojago.trip.common.exception.GabojagoApiException;
import org.springframework.http.HttpStatus;

public class NicknameAlreadyExistException extends GabojagoApiException {

    public NicknameAlreadyExistException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}
