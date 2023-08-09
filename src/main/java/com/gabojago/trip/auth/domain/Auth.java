package com.gabojago.trip.auth.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@ToString
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;//unique
    private String refreshToken;

    @Builder
    public Auth(Integer userId, String refreshToken) {
        this.userId = userId;
        this.refreshToken = refreshToken;
    }
}
