package com.gabojago.trip.route.domain;

import com.gabojago.trip.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Companion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private CompanionGrant companionGrant; // 권한 레벨

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_route_id")
    private TripRoute tripRoute; // 여행계획아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User creator; // 동행자아이디

}
