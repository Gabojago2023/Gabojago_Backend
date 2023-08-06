package com.gabojago.trip.route.domain;

import com.gabojago.trip.place.domain.Place;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class TripPlace {
    @Id
    private Long id;
    @ManyToOne
    TripRoute tripRoute;
    @ManyToOne
    Place place;
}
