package com.gabojago.trip.route.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.gabojago.trip.user.domain.User;
@Entity
@Getter
@NoArgsConstructor
public class TripRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String name;
    private LocalDateTime createdAt;
    private String tripImgUrl;
    private Integer favoritePlaceId;
    private String content;
    private boolean isPublic;
    private LocalDateTime startDate;

    @ManyToOne
    private User user;


}
