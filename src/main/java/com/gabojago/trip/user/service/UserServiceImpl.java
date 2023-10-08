package com.gabojago.trip.user.service;

import com.gabojago.trip.auth.oauth.OAuthUserInfo;
import com.gabojago.trip.user.domain.User;
import com.gabojago.trip.user.domain.UserRepository;
import com.gabojago.trip.user.dto.MailDto;
import com.gabojago.trip.user.dto.NicknameDto;
import com.gabojago.trip.user.dto.RandomNicknameDto;
import com.gabojago.trip.user.dto.UserDto;
import com.gabojago.trip.user.exception.NicknameAlreadyExistException;
import com.gabojago.trip.user.exception.UserNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;

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
            if (findUser != null && findUser.getId() != id) {
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
    public NicknameDto isNicknameAvailable(Integer userId, String nickname) {
        boolean isAvailable = userRepository.existsByNicknameAndIdNot(nickname, userId);
        return new NicknameDto(nickname, isAvailable);
    }

    @Override
    public RandomNicknameDto getRandomNickname() {
        List<User> user = userRepository.findAllUser();
        int maxCnt = user.size();
        int randIdx = (int)(Math.random() * maxCnt);
        RandomNicknameDto nicknameDto = new RandomNicknameDto(user.get(randIdx).getId(), user.get(randIdx).getNickname());
        return nicknameDto;
    }

    @Override
    public MailDto createMailAndChangePassword(String userEmail) {
        String str = getTempPassword();
        MailDto mailDto = new MailDto();
        mailDto.setAddress(userEmail);
        mailDto.setTitle("OnePick 임시비밀번호 안내 이메일 입니다.");
        mailDto.setMessage(
                "안녕하세요. OnePick 임시비밀번호 안내 관련 이메일 입니다." + " 회원님의 임시 비밀번호는 " + str + " 입니다."
                        + "로그인 후에 비밀번호를 변경을 해주세요");

        // BCrypt 적용 후 DB에 저장
//        if (updatePassword(BCrypt.hashpw(str, BCrypt.gensalt()), userEmail) == 1) {
//        if (updatePassword(str, userEmail) == 1) {
//            return mailDto;
//        } else {
//            return null;
//        }

        updatePassword(str, userEmail);
        return mailDto;
    }

    @Override
    public void updatePassword(String str, String userEmail) {
        String newPassword = str;

        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new UserNotFoundException("존재하지 않는 이메일입니다");
        }
        user.setPassword(newPassword);

        userRepository.save(user);
    }

    @Override
    public String getTempPassword() {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
                'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z'};
        String str = "";

        int idx;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }

    @Override
    public void mailSend(MailDto mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        message.setFrom("phsk710@naver.com");
        message.setReplyTo("phsk710@naver.com");
        mailSender.send(message);
    }
}
