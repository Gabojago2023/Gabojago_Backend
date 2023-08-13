package com.gabojago.trip.place.exception;

import com.gabojago.trip.common.exception.GabojagoApiException;
import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends GabojagoApiException {
    public CommentNotFoundException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason+" 는 존재하지 않습니다.");
    }
}