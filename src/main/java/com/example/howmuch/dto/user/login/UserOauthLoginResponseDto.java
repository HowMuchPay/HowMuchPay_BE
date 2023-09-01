package com.example.howmuch.dto.user.login;

import lombok.*;


/**
 * 1. 프론트 로부터 받은 access token & refresh token 을 받아서 서비스 토큰 발급
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserOauthLoginResponseDto {
    private String tokenType;
    private String accessToken;
    private String refreshToken;
    private String expiredTime;
}
