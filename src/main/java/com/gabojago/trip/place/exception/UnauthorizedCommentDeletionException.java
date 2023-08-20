package com.gabojago.trip.place.exception;

import com.gabojago.trip.common.exception.GabojagoApiException;
import org.springframework.http.HttpStatus;

public class UnauthorizedCommentDeletionException extends GabojagoApiException {
    public UnauthorizedCommentDeletionException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason+" 권한이 존재하지 않습니다.");
    }
}