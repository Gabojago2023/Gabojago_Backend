package com.gabojago.trip.route.dto;

import com.gabojago.trip.route.domain.TripPlace;
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

    public static TripPlaceDto from(TripPlace tripPlace) {
        return TripPlaceDto.builder()
                .placeId(tripPlace.getPlace().getId())
                .tripOrder(tripPlace.getTripOrder())
                .tripDay(tripPlace.getTripDay())
                .build();
    }
}