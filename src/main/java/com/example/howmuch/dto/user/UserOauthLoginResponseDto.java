package com.example.howmuch.dto.user;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserOauthLoginResponseDto {

    private String tokenType;
    private String accessToken;
    private String refreshToken;
    private String expiredTime; // access token 의 만료 날짜
}
