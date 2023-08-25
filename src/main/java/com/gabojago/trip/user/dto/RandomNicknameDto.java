package com.gabojago.trip.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class RandomNicknameDto {

    private Integer userId;
    private String nickname;

    public RandomNicknameDto(Integer userId, String nickname) {
        this.userId = userId;
        this.nickname = nickname;
    }
}
