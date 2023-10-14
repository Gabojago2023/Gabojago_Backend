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

    private Integer userId;

    private String name;
    private String nickname;
    private String commentText;
    private int starRating;
    private String createdDate;

    public CommentResponseDto(Integer id, Integer placeId, Integer userId, String name,
            String nickname, String commentText,
            int starRating, String createdDate) {
        this.id = id;
        this.placeId = placeId;
        this.userId = userId;
        this.name = name;
        this.nickname = nickname;
        this.commentText = commentText;
        this.starRating = starRating;
        this.createdDate = createdDate;
    }
}
