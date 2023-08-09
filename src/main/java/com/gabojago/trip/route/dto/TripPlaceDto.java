package com.gabojago.trip.route.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TripPlaceDto {
    private int tripDay;
    private int tripOrder;
    private int placeId;
    @Builder
    public TripPlaceDto(int tripDay, int tripOrder, int placeId) {
        this.tripDay = tripDay;
        this.tripOrder = tripOrder;
        this.placeId = placeId;
    }
}