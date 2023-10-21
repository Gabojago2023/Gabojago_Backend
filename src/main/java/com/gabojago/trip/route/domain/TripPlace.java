package com.gabojago.trip.route.domain;

import com.gabojago.trip.place.domain.Place;
import com.gabojago.trip.route.dto.TripPlaceDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class TripPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLACE_ID")
    private Place place;
    private int tripDay;
    private int tripOrder;
    @ManyToOne(fetch = FetchType.LAZY)
    private TripRoute tripRoute;

    @Builder
    public TripPlace(Integer id, Place place, int tripDay, int tripOrder, TripRoute tripRoute) {
        this.id = id;
        this.place = place;
        this.tripDay = tripDay;
        this.tripOrder = tripOrder;
        this.tripRoute = tripRoute;
    }

    public static TripPlace from(TripPlaceDto dto, TripRoute tripRoute, Place place) {
        return TripPlace.builder()
                .place(place)
                .tripDay(dto.getTripDay())
                .tripOrder(dto.getTripOrder())
                .tripRoute(tripRoute)
                .build();
    }
}
