package com.gabojago.trip.place.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private Long longitude;
    private Long latitude;
    private String address;
    private String category;
    private String imgUrl; // img_url
    private String imgUrl2;
    private int sidoCode;
    private int gugunCode;
    private String overview;

}
