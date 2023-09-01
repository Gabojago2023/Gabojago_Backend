package com.gabojago.trip.place.service;

import com.gabojago.trip.place.dto.response.CommentResponseDto;
import org.springframework.data.domain.Slice;

public interface CommentService {

    void addCommentToPlace(Integer placeId, Integer userId, String commentText,
            Integer starRating);

    Slice<CommentResponseDto> getCommentsByPlaceId(Integer placeId, Integer cursor, Integer size);

    void updateComment(Integer userId, Integer placeId, Integer commentId, String newCommentText,
            Integer newStartRating);

    void deleteCommentById(Integer userId, Integer commentId);
}
