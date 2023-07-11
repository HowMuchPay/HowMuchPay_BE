package com.example.howmuch.dto.member;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NeedDataUserOauthResponseDto {

    private String name;

    private String nickName;

    private String oauthId;
}
