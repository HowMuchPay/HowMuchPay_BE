package com.example.howmuch.config.security;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    private String tokenValue;
    private Long expiredTime;
}
