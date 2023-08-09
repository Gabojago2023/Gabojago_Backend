package com.gabojago.trip.route.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@ToString
@Getter
@Setter
public class TripRouteModifyDto {
    private Integer id;
    private String name;
    private String content;
    private String image;
    private LocalDate startDate;
    private boolean isPublic;

    private Integer bestPlaceId;
    private List<TripPlaceDto> tripPlaces;
}
