package com.gabojago.trip.place.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Gugun {

    @Id
    private Integer gugunCode;

    @Column(length = 30)
    private String gugunName;

    @ManyToOne
    @JoinColumn(name = "sido_code")
    private Sido sido;
}
