package com.example.howmuch.dto.user;

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
