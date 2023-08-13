package com.gabojago.trip.place.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Integer id;
    private Integer placeId;
    private String nickname;
    private String commentText;

    public CommentResponseDto(Integer id, Integer placeId, String nickname, String commentText,
            int starRating, String createdDate) {
        this.id = id;
        this.placeId = placeId;
        this.nickname = nickname;
        this.commentText = commentText;
        this.starRating = starRating;
        this.createdDate = createdDate;
    }

    private int starRating;
    private String createdDate;
}
