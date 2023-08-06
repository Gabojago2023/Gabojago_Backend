package com.gabojago.trip.route.dto;


import java.time.LocalDateTime;
import java.util.List;

import com.gabojago.trip.route.domain.TripRoute;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@ToString
@Getter
@Setter
@NoArgsConstructor
public class TripRouteResDto {
    private Integer id;
    private String name;
    private LocalDateTime createdAt;
    private Integer userId;
    private String tripImgUrl;
    private Integer bestPlaceId;
    private List<TripPlaceDto> tripPlaces;

    @Builder
    public TripRouteResDto(int id, String name, LocalDateTime createdAt, int userId, String tripImgUrl, Integer bestPlaceId,
                           List<TripPlaceDto> tripPlaces) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.userId = userId;
        this.tripImgUrl = tripImgUrl;
        this.bestPlaceId = bestPlaceId;
        this.tripPlaces = tripPlaces;
    }

    public static TripRouteResDto from(TripRoute tripRoute) {
        return TripRouteResDto.builder()
                .id(tripRoute.getId())
                .name(tripRoute.getName())
                .createdAt(tripRoute.getCreatedAt())
                .userId(tripRoute.getUser().getId())
                .tripImgUrl(tripRoute.getTripImgUrl())
                .bestPlaceId(tripRoute.getBestPlaceId())
                .build();
    }
}
