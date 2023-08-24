package com.gabojago.trip.user.domain;

import com.gabojago.trip.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String password; // 비밀번호
    private String image;
    private Boolean isAdmin;

    private String name;
    private String nickname;

    private String provider;
    private String providerId;
    private int streak;

    public User(Integer id) {
        this.id = id;
    }

    @Builder
    public User(Integer id, String email, String password, String image, Boolean isAdmin,
        String name,
        String nickname, String provider, String providerId, int streak) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.image = image;
        this.isAdmin = isAdmin;
        this.name = name;
        this.nickname = nickname;
        this.provider = provider;
        this.providerId = providerId;
        this.streak = streak;
    }

    public static User from(UserDto userDto) {
        return User.builder()
            .email(userDto.getEmail())
            .nickname(userDto.getNickname())
            .image(userDto.getImage())
            .build();
    }
}
