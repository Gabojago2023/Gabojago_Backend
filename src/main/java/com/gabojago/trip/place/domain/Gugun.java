package com.gabojago.trip.place.domain;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

    @OneToMany(mappedBy = "gugun")
    private List<Place> places;
}
