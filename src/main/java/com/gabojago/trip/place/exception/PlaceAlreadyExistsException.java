package com.gabojago.trip.place.exception;

import com.gabojago.trip.common.exception.GabojagoApiException;
import org.springframework.http.HttpStatus;

public class PlaceAlreadyExistsException extends GabojagoApiException {
    public PlaceAlreadyExistsException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason + "는 이미 존재합니다.");
    }
}
