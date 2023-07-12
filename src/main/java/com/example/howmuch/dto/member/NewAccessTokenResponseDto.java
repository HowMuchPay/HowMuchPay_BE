package com.example.howmuch.dto.member;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewAccessTokenResponseDto {
    private String accessToken;
    private String expiredTime;
}
