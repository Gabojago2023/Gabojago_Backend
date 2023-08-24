package com.gabojago.trip.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NicknameDto {
    private String nickname;
    private boolean isAvailable;
}
