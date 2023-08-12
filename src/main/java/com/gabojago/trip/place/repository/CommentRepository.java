package com.gabojago.trip.place.repository;

import com.gabojago.trip.place.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
