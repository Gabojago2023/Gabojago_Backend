package com.gabojago.trip.route.domain;

import com.gabojago.trip.user.domain.User;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Companion {

    @Id
    private Integer id;
    @ManyToOne
    private TripRoute tripRoute;
    @ManyToOne
    private User user; // 동행자
    private String grant;
}
