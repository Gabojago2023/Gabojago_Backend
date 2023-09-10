package com.gabojago.trip.user.dto;

import com.gabojago.trip.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResDto {
    private Integer id;
    private String name;
    private String nickname;
    private String email;
    private String image;

    @Builder
    public UserResDto(Integer id, String name, String nickname, String email, String image) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.image = image;
    }

    public static UserResDto from(User user) {
        return UserResDto.builder()
                .id(user.getId())
            .name(user.getName())
            .nickname(user.getNickname())
            .email(user.getEmail())
            .image(user.getImage())
            .build();
    }
}
