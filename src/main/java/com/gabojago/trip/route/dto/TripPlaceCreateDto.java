package com.gabojago.trip.route.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TripPlaceCreateDto {
    private int tripDay;
    private int tripOrder;
    private int placeId;

}