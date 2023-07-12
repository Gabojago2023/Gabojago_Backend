package com.gabojago.trip.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class GabojagoApiException extends ResponseStatusException {
    public GabojagoApiException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
