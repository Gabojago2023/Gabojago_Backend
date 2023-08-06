package com.gabojago.trip.user.service;

import com.gabojago.trip.auth.oauth.OAuthUserInfo;
import com.gabojago.trip.user.exception.UserNotFoundException;
import com.gabojago.trip.user.domain.User;
import com.gabojago.trip.user.domain.UserRepository;
import com.gabojago.trip.user.dto.UserDto;
import com.gabojago.trip.user.dto.UserResDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(UserDto userDto) {
        User user = User.from(userDto);
        userRepository.save(user);
    }

    @Override
    public User getUser(int userId) {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new UserNotFoundException("" + userId)
        );
        return user;
    }

    @Override
    public int modifyUser(int userId, UserDto userReqDto) {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new UserNotFoundException("" + userId)
        );
        if (userReqDto.getNickname() != null) {
            user.setNickname(userReqDto.getNickname());
        }
        if (userReqDto.getImage() != null) {
            user.setImage(userReqDto.getImage());
        }
        return userRepository.save(user).getId();
    }

    @Override
    public List<UserDto> getAllUser() {
        List<UserDto> userList = userRepository.findAll().stream()
            .map(UserDto::from)
            .collect(Collectors.toList());
        return userList;
    }

    @Override
    public void delete(int userId) {
        userRepository.delete(
            userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("" + userId)
            )
        );
    }

    @Override
    public User getUserByProviderId(String provider, String providerId) {
        return userRepository.findByProviderAndProviderId(provider, providerId);
    }

    @Override
    public User addOAuthUser(OAuthUserInfo oauthUser) {
        User newUser = User.builder()
            .name(oauthUser.getName())
            .email(oauthUser.getEmail())
            .provider(oauthUser.getProvider())
            .providerId(oauthUser.getProviderId())
            .isAdmin(false)
            .build();
        return userRepository.save(newUser);
    }
}
