package com.gabojago.trip.route.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.gabojago.trip.route.dto.TripRouteCreateDto;
import com.gabojago.trip.route.dto.TripRouteModifyDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.gabojago.trip.user.domain.User;
@Entity
@Getter
@NoArgsConstructor
public class TripRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String name;
    private String tripImgUrl;
    private String content;
    private LocalDate startDate;
    private Integer bestPlaceId;
    private boolean isPublic;
    private LocalDateTime createdAt;

    @ManyToOne
    private User user;

    @Builder
    public TripRoute(Integer id, String name, LocalDateTime createdAt, String tripImgUrl, Integer bestPlaceId, String content, boolean isPublic, LocalDate startDate, User user) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.tripImgUrl = tripImgUrl;
        this.bestPlaceId = bestPlaceId;
        this.content = content;
        this.isPublic = isPublic;
        this.startDate = startDate;
        this.user = user;
    }

    public static TripRoute from(TripRouteCreateDto tripRouteCreateDto, User user) {
        return TripRoute.builder()
                .name(tripRouteCreateDto.getName())
                .content(tripRouteCreateDto.getContent())
                .tripImgUrl(tripRouteCreateDto.getImage())
                .startDate(tripRouteCreateDto.getStartDate())
                .bestPlaceId(tripRouteCreateDto.getBestPlaceId())
                .isPublic(tripRouteCreateDto.isPublic())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();
    }

    public static TripRoute from(TripRouteModifyDto dto, User user) {
        return TripRoute.builder()
                .name(dto.getName())
                .content(dto.getContent())
                .tripImgUrl(dto.getImage())
                .startDate(dto.getStartDate())
                .bestPlaceId(dto.getBestPlaceId())
                .isPublic(dto.isPublic())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();
    }

}
