package com.gabojago.trip.place.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Sido {

    @Id
    private Integer sidoCode;

    @Column(length = 30)
    private String sidoName;

    private String imageUrl;
}
