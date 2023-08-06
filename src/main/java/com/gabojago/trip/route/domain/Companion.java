package com.gabojago.trip.route.domain;

import com.gabojago.trip.user.domain.User;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Companion {

    @Id
    private Integer id;
    @Enumerated(EnumType.STRING)
    private CompanionGrant grant; // 권한 레벨
    @ManyToOne
    private TripRoute tripRoute; // 여행계획아이디
    @ManyToOne
    private User user; // 동행자아이디

}
