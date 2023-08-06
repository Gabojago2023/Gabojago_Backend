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
    @ManyToOne
    private Place place;
    private int tripDay;
    private int tripOrder;
    @ManyToOne
    private TripRoute tripRoute;


}
