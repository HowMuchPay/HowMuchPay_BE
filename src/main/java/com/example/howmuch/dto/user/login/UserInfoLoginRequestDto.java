package com.example.howmuch.dto.user.login;

import com.example.howmuch.constant.RoleType;
import com.example.howmuch.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserInfoLoginRequestDto {
    @NotBlank
    private String oauthId;
    @NotBlank
    private String nickname;
    @NotBlank
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
