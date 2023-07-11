package com.example.howmuch.dto.member;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActiveUserOauthResponseDto {

    private String tokenType;
    private String accessToken;
    private String refreshToken;
    private String expiredTime;
}
