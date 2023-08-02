package com.gabojago.trip.place.domain;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

    @OneToMany(mappedBy = "sido")
    private List<Place> places;

}
