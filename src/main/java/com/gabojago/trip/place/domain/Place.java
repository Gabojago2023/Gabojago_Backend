package com.gabojago.trip.place.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@ToString
@Getter
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(length = 100)
    private String name;

    @Column(precision = 20, scale = 17)
    private BigDecimal longitude;
    @Column(precision = 20, scale = 17)
    private BigDecimal latitude;

    @Column(length = 100)
    private String address;
    @Column(length = 45)
    private String category;
    @Column(length = 200)
    private String imgUrl; // img_url
    @Column(length = 200)
    private String imgUrl2; // img_url

    @Column(length = 10000)
    private String overview;

    @ManyToOne
    @JoinColumn(name = "sido_code")
    private Sido sido;

    @ManyToOne
    @JoinColumn(name = "gugun_code")
    private Gugun gugun;

}
