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
    private PlaceDetailDto place;
    //private int placeId;
    @Builder
    public TripPlaceDto(int tripDay, int tripOrder, PlaceDetailDto place) {
        this.tripDay = tripDay;
        this.tripOrder = tripOrder;
        this.place = place;
    }

    public static TripPlaceDto from(TripPlace tripPlace) {
        return TripPlaceDto.builder()
                .place(PlaceDetailDto.from(tripPlace.getPlace()))
                .tripOrder(tripPlace.getTripOrder())
                .tripDay(tripPlace.getTripDay())
                .build();
    }
}