package com.gabojago.trip.route.domain;

import com.gabojago.trip.user.domain.User;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import static javax.persistence.EnumType.STRING;

@Entity
public class Companion {

    @Id
    private Integer id;
    @Enumerated(STRING)
    private CompanionGrant grant; // 권한 레벨
    @ManyToOne
    private TripRoute tripRoute; // 여행계획아이디
    @ManyToOne
    private User user; // 동행자아이디

}
