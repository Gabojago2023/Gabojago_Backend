package com.gabojago.trip.place.service;

import com.gabojago.trip.place.dto.response.CommentResponseDto;
import java.util.List;

public interface CommentService {
    void addCommentToPlace(Integer placeId, Integer userId, String commentText,
            Integer starRating);

    List<CommentResponseDto> getCommentsByPlaceId(Integer placeId);

    void updateComment(Integer userId, Integer placeId, Integer commentId, String newCommentText, Integer newStartRating);
}
