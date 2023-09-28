package com.example.howmuch.service.user;


import com.example.howmuch.domain.entity.User;
import com.example.howmuch.dto.user.login.UserOauthLoginResponseDto;
import com.example.howmuch.exception.user.InvalidTokenException;
import com.example.howmuch.exception.user.UnauthorizedUserException;
import com.example.howmuch.util.AuthTransformUtil;
import com.example.howmuch.util.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
/*
    access token 재 발급시 access token + refresh token 같이 보내는 이유
   -> refresh token 은 redis 에 저장되어 있는데 회원의 id가 필요 이 정보는 access token 에 저장되어 있음
*/

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final JwtService jwtService;
    private final UserService userService;
    private final OauthService oauthService;

    @Transactional
    /* jwt 만료시 access token 재발급 해주는 메소드 with 만료된 access token + refresh token */
    public UserOauthLoginResponseDto accessTokenByRefreshToken(String accessToken, String refreshToken) {
        // 1. refresh token 유효성
        validationRefreshToken(refreshToken);
        // 2. 요청 refresh token 과 redis refresh token 동일성 검증
        isRefreshTokenMatch(refreshToken);

        // 3. 기존 refresh token 을 redis 삭제 + 새로운 refresh token & access token 재발급
        User user = this.userService.findUserFromToken();
        return this.oauthService.oauthLoginResult(user);
    }

    @Transactional
    public void logout(HttpServletRequest request) {
        // access token 가져오기
        String accessToken = AuthTransformUtil.resolveAccessTokenFromRequest(request);
        this.userService.findUserFromToken().deleteRefreshToken();
    }

    @Transactional
    public User findUserByToken(String accessToken) {
        Long id = Long.parseLong(jwtService.getPayLoad(accessToken));
        return userService.findById(id);
    }

    private void validationRefreshToken(String refreshToken) {
        if (!this.jwtService.validateToken(refreshToken)) {
            throw new UnauthorizedUserException("인가되지 않은 Refresh Token 입니다.");
        }
    }

    private void isRefreshTokenMatch(String refreshToken) {
        User user = this.userService.findUserFromToken();
        if (!user.getRefreshToken().equals(refreshToken)) {
            throw new InvalidTokenException("유효하지 않은 리프레시 토큰입니다.");
        }
    }
}
