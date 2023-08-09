package com.gabojago.trip.route.exception;

import com.gabojago.trip.common.exception.NotFoundException;
import org.springframework.http.HttpStatus;

public class TripRouteNotFoundException extends NotFoundException {

    public TripRouteNotFoundException(String reason) {
        super(reason);
    }
}
