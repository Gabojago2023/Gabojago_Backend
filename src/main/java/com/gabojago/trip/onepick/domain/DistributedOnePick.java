package com.gabojago.trip.onepick.domain;

import com.gabojago.trip.user.domain.User;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@NoArgsConstructor
@ToString
@Getter
@Setter
public class DistributedOnePick {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private Double rate;

    @Column(length = 255)
    private String description;

    @CreationTimestamp
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "onepick_id")
    private OnePick onePick;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Boolean liked;

    @Builder
    public DistributedOnePick(Double rate, OnePick onePick, User user) {
        this.rate = rate;
        this.onePick = onePick;
        this.user = user;
    }
}
