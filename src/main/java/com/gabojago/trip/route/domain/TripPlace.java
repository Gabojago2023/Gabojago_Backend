package com.gabojago.trip.route.domain;

import com.gabojago.trip.place.domain.Place;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.NoArgsConstructor;

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
