package com.gabojago.trip.user.dto;

import com.gabojago.trip.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@NoArgsConstructor
public class UserDto {
    private String nickname;
    private String email;
    private String image;

    @Builder
    public UserDto( String nickname, String email, String image) {
        this.nickname = nickname;
        this.email = email;
        this.image = image;
    }

    public static UserDto from(User user) {
        return UserDto.builder()
            .nickname(user.getNickname())
            .email(user.getEmail())
            .image(user.getImage())
            .build();
    }
}
