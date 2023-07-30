package com.gabojago.trip.place.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private String name;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String address;
    private String category;
    private String imgUrl; // img_url
    private String imgUrl2;
    private int sidoCode;
    private int gugunCode;
    private String overview;

}
