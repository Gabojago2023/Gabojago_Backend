package com.gabojago.trip.route.exception;

import com.gabojago.trip.common.exception.NotFoundException;

public class TripRouteNotFoundException extends NotFoundException {

    public TripRouteNotFoundException(String reason) {
        super(reason);
    }
}
