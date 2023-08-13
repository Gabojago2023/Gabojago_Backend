package com.gabojago.trip.place.domain;

import java.sql.Timestamp;

import com.gabojago.trip.user.domain.User;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@ToString
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String commentText;
    private Integer starRating;

    @CreationTimestamp
    private Timestamp createdDate;

    public Comment(Place place, User user, String commentText, Integer starRating) {
        this.place = place;
        this.user = user;
        this.commentText = commentText;
        this.starRating = starRating;
    }

    public void update(String newCommentText, Integer newStarRating) {
        this.commentText = newCommentText;
        this.starRating = newStarRating;
    }
}
