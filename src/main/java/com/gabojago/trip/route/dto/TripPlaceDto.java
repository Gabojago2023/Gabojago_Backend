package com.gabojago.trip.route.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TripPlaceDto {
    private int id;
    private int placeId;
    private int tripRouteId;
    private int tripDay;
    private int tripOrder;
    private PlaceDetailDto place;

    @Builder
    public TripPlaceDto(int id, int placeId, int tripRouteId, int tripDay, int tripOrder, PlaceDetailDto place) {
        this.id = id;
        this.place = place;
        this.tripRouteId = tripRouteId;
        this.tripDay = tripDay;
        this.tripOrder = tripOrder;
        this.placeId = placeId;
    }
    public TripPlaceDto(int id, int placeId, int tripRouteId, int tripDay, int tripOrder) {
        this.id = id;
        this.placeId = placeId;
        this.tripRouteId = tripRouteId;
        this.tripDay = tripDay;
        this.tripOrder = tripOrder;
    }
//    @Builder
//    public TripPlaceDto(int id, int tripRouteId, int tripDay, int tripOrder) {
//        this.id = id;
//        this.tripRouteId = tripRouteId;
//        this.tripDay = tripDay;
//        this.tripOrder = tripOrder;
//    }
//    @Builder
//    public TripPlaceDto(int id, int placeId, int tripRouteId, int tripDay, int tripOrder) {
//        this.id = id;
//        this.placeId = placeId;
//        this.tripRouteId = tripRouteId;
//        this.tripDay = tripDay;
//        this.tripOrder = tripOrder;
//    }
}