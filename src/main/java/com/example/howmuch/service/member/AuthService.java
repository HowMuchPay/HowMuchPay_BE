package com.example.howmuch.service.member;

import com.example.howmuch.contant.Token;
import com.example.howmuch.dto.member.NewAccessTokenResponseDto;
import com.example.howmuch.exception.member.InvalidTokenException;
import com.example.howmuch.exception.member.UnauthorizedMemberException;
import com.example.howmuch.util.JwtService;
import com.example.howmuch.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/*
    access token 재 발급시 access token + refresh token 같이 보내는 이유
   -> refresh token 은 redis 에 저장되어 있는데 회원의 id가 필요 이 정보는 access token 에 저장되어 있음
*/

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final JwtService jwtService;
    private final RedisUtil redisUtil;

    /* jwt 만료시 access token 재발급 해주는 메소드 with 만료된 access token + refresh token */
    public NewAccessTokenResponseDto accessTokenByRefreshToken(String accessToken,
                                                               String refreshToken) {
        validationRefreshToken(refreshToken);
        String id = this.jwtService.getPayLoad(accessToken);
        // key : 회원의 id(String) + value : refresh token
        // key 로 redis 에서 refresh token 조회
        String storedRefreshToken = this.redisUtil.getData(id);

        if (!storedRefreshToken.equals(refreshToken)) {
            throw new InvalidTokenException("유효하지 않은 엑세스 토큰입니다.");
        }

        Token newAccessToken = this.jwtService.createAccessToken(id);
        LocalDateTime expiredTime = LocalDateTime.now().plusSeconds(newAccessToken.getExpiredTime() / 1000);
        return NewAccessTokenResponseDto.builder()
                .accessToken(newAccessToken.getTokenValue())
                .expiredTime(expiredTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

    private void validationRefreshToken(String refreshToken) {
        if (!this.jwtService.validateToken(refreshToken)) {
            {
                throw new UnauthorizedMemberException("인가되지 않은 Refresh Token 입니다.");
            }
        }
    }
}
