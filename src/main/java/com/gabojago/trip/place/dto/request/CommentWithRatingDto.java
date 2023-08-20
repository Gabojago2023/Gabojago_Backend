package com.gabojago.trip.place.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class CommentWithRatingDto {

    Integer rate;
    String comment;
}
