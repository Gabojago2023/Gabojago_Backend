package com.gabojago.trip.place.repository;

import com.gabojago.trip.place.domain.Comment;
import com.gabojago.trip.place.dto.response.CommentResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT new com.gabojago.trip.place.dto.response.CommentResponseDto(c.id, c.place.id, u.id, u.nickname, c.commentText, c.starRating, "
            +
            "CASE WHEN FUNCTION('date_format', c.createdDate, '%Y%m%d') = FUNCTION('date_format', CURRENT_TIMESTAMP, '%Y%m%d') "
            +
            "THEN FUNCTION('date_format', c.createdDate, '%H:%i:%s') " +
            "ELSE FUNCTION('date_format', c.createdDate, '%y.%m.%d') END) " +
            "FROM Comment c " +
            "INNER JOIN User u on c.user.id = u.id " +
            "WHERE c.place.id = :placeId " +
            "ORDER BY c.createdDate DESC")
    List<CommentResponseDto> findCommentsByPlaceId(@Param("placeId") Integer placeId,
            Pageable pageable);

    @Query("SELECT new com.gabojago.trip.place.dto.response.CommentResponseDto(c.id, c.place.id, u.id, u.nickname, c.commentText, c.starRating, "
            +
            "CASE WHEN FUNCTION('date_format', c.createdDate, '%Y%m%d') = FUNCTION('date_format', CURRENT_TIMESTAMP, '%Y%m%d') "
            +
            "THEN FUNCTION('date_format', c.createdDate, '%H:%i:%s') " +
            "ELSE FUNCTION('date_format', c.createdDate, '%y.%m.%d') END) " +
            "FROM Comment c " +
            "INNER JOIN User u on c.user.id = u.id " +
            "WHERE c.place.id = :placeId " +
            "AND c.id < :cursor " +
            "ORDER BY c.createdDate DESC")
    List<CommentResponseDto> findNextCommentsByPlaceId(@Param("placeId") Integer placeId,
            @Param("cursor") Integer cursor, Pageable pageable);
}
