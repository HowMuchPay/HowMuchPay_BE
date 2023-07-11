package com.example.howmuch.contant;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    private String tokenValue;
    private String expiredTime;
}
