package com.gabojago.trip.route.dto;


import java.sql.Timestamp;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter
@NoArgsConstructor
public class TripRouteDto {
    private int id;
    private String name;
    private Timestamp createdAt;
    private int userId;
    private String tripImgUrl;
    private int favorite;
    private List<TripPlaceDto> tripPlaces;


    @Builder
    public TripRouteDto(int id, String name, Timestamp createdAt, int userId, String tripImgUrl, int favorite,
            List<TripPlaceDto> tripPlaces) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.userId = userId;
        this.tripImgUrl = tripImgUrl;
        this.favorite = favorite;
        this.tripPlaces = tripPlaces;
    }
}
