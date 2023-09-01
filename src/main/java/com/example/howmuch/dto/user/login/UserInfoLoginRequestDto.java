package com.example.howmuch.dto.user.login;

import com.example.howmuch.constant.RoleType;
import com.example.howmuch.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserInfoLoginRequestDto {
    private String oauthId;
    private String nickname;
    private String profileImageUrl;

    public User toEntity() {
        return User.builder()
                .oauthId(oauthId)
                .nickname(nickname)
                .profileImage(profileImageUrl)
                .roleType(RoleType.ROLE_USER)
                .userTotalPayAmount(0L)
                .userTotalReceiveAmount(0L)
                .build();
    }
}
