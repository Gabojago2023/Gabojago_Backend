package com.gabojago.trip.place.exception;

import com.gabojago.trip.common.exception.GabojagoApiException;
import org.springframework.http.HttpStatus;

public class PlaceNotFoundException extends GabojagoApiException {
    public PlaceNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason+" 는 존재하지 않습니다.");
    }
}