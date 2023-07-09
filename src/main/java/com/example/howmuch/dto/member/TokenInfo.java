package com.example.howmuch.dto.member;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenInfo {

    private String grantType;

    private String accessToken;

    private String refreshToken;
}
