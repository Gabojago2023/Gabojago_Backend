package com.gabojago.trip.route.domain;

import com.gabojago.trip.place.domain.Place;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
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
    @JoinColumn(name = "TRIP_ROUTE_ID")
    private TripRoute tripRoute;


}
