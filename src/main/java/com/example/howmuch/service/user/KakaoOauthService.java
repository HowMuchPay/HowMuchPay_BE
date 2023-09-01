package com.example.howmuch.service.user;

import com.example.howmuch.config.security.Token;
import com.example.howmuch.domain.entity.User;
import com.example.howmuch.domain.repository.UserRepository;
import com.example.howmuch.dto.user.login.UserInfoLoginRequestDto;
import com.example.howmuch.dto.user.login.UserOauthLoginResponseDto;
import com.example.howmuch.util.JwtService;
import com.example.howmuch.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoOauthService {

    /**
     * 프론트에서 넘어온 토큰 서비스 토큰으로 변경해서 저장
     * 프론트에서 넘어온 User 정보 저장
     **/
    private static final String BEARER_TYPE = "Bearer";
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RedisUtil redisUtil;

    public UserOauthLoginResponseDto getOauth(UserInfoLoginRequestDto userInfoLoginRequestDto) {
        // 1. UserInfoLoginRequestDto 로 회원 정보 저장
        // 2. 서비스 토큰 발급 through UserOauthLoginResponseDto
        User savedUser = saveUser(userInfoLoginRequestDto);
        return processKakaoToken(savedUser);
    }

    private User saveUser(UserInfoLoginRequestDto userInfoLoginRequestDto) {
        Optional<User> optionalUser
                = this.userRepository.findByOauthId(userInfoLoginRequestDto.getOauthId());

        return optionalUser.orElseGet(() -> this.userRepository.save(userInfoLoginRequestDto.toEntity()));
        // null 일씨 매개변수의 supplier 호출
    }


    private UserOauthLoginResponseDto processKakaoToken(User user) {
        Token accessToken = this.jwtService.createAccessToken(String.valueOf(user.getId()));
        Token refreshToken = this.jwtService.createRefreshToken();
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(accessToken.getExpiredTime() / 1000);
//        this.redisUtil.setDataExpire(String.valueOf(user.getId()), refreshToken.getTokenValue(), refreshToken.getExpiredTime());


        return UserOauthLoginResponseDto.builder()
                .tokenType(BEARER_TYPE)
                .accessToken(BEARER_TYPE + " " + accessToken.getTokenValue())
                .expiredTime(expireTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))) // 만료 Local Date Time
                .refreshToken(refreshToken.getTokenValue())
                .build();

    }
}
