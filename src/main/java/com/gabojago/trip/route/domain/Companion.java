package com.gabojago.trip.route.domain;

import com.gabojago.trip.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@Entity
@NoArgsConstructor
public class Companion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private CompanionGrant grant; // 권한 레벨
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRIP_ROUTE_ID")
    private TripRoute tripRoute; // 여행계획아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user; // 동행자아이디

}
