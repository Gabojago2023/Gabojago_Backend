package com.gabojago.trip.place.service;

public interface CommentService {
    void addCommentToPlace(Integer placeId, Integer userId, String commentText,
            Integer starRating);
}
