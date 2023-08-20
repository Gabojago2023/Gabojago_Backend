package com.gabojago.trip.place.exception;

import com.gabojago.trip.common.exception.GabojagoApiException;
import org.springframework.http.HttpStatus;

public class PlaceScrapNotFoundException extends GabojagoApiException {

    public PlaceScrapNotFoundException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason + "는 이미 존재하지 않습니다");
    }
}
