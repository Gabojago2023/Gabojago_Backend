package com.gabojago.trip.user.service;

import com.gabojago.trip.auth.oauth.OAuthUserInfo;
import com.gabojago.trip.user.domain.User;
import com.gabojago.trip.user.domain.UserRepository;
import com.gabojago.trip.user.dto.NicknameDto;
import com.gabojago.trip.user.dto.UserDto;
import com.gabojago.trip.user.exception.NicknameAlreadyExistException;
import com.gabojago.trip.user.exception.UserNotFoundException;
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
    public void modifyUser(Integer id, String nickname, String imagePath) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("" + id));

        // 닉네임 바꾸는 경우
        if (nickname != null && !nickname.isBlank()) {
            // 다른 사람이 바꾸고자하는 닉네임 이미 사용하고 있음
            User findUser = userRepository.findByNickname(nickname);
            // 만약 유저 닉네임이 중복이라면 함수 종료
            if (findUser != null && findUser.getId()!=id) {
                throw new NicknameAlreadyExistException("이미 사용중인 닉네임 입니다.");
            }
            user.setNickname(nickname);
        }
        // 이미지 바꾸는 경우
        if (imagePath != null && !imagePath.isBlank()) {
            user.setImage(imagePath);
        }
        // DB에 저장한다.
        userRepository.save(user);
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

    @Override
    public NicknameDto isNicknameAvailable(Integer userId , String nickname){
        boolean isAvailable = userRepository.existsByNicknameAndIdNot(nickname,userId);
        return new NicknameDto(nickname, isAvailable);
    }
}
