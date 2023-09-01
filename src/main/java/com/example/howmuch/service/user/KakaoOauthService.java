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

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoOauthService {

    private static final String BEARER_TYPE = "Bearer";
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RedisUtil redisUtil;

    public UserOauthLoginResponseDto getOauth(UserInfoLoginRequestDto userInfoLoginRequestDto) {
        return processKakaoToken(saveUser(userInfoLoginRequestDto));
    }

    private User saveUser(UserInfoLoginRequestDto userInfoLoginRequestDto) {
        return this.userRepository.findByOauthId(userInfoLoginRequestDto.getOauthId()).orElseGet(()
                -> this.userRepository.save(userInfoLoginRequestDto.toEntity())
        );
    }


    private UserOauthLoginResponseDto processKakaoToken(User user) {
        Token accessToken = this.jwtService.createAccessToken(String.valueOf(user.getId()));
        Token refreshToken = this.jwtService.createRefreshToken();
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(accessToken.getExpiredTime() / 1000);
//        this.redisUtil.setDataExpire(String.valueOf(user.getId()), refreshToken.getTokenValue(), refreshToken.getExpiredTime());
        return UserOauthLoginResponseDto.builder()
                .tokenType(BEARER_TYPE)
                .accessToken(BEARER_TYPE + " " + accessToken.getTokenValue())
                .expiredTime(expireTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .refreshToken(refreshToken.getTokenValue())
                .build();
    }
}
