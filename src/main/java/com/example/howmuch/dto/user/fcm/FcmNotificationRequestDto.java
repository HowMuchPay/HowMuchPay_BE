package com.example.howmuch.dto.user.fcm;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FcmNotificationRequestDto {
    //사용자들의 토큰을 서버에 저장해둔 상태를 가정 추후 리팩토링 필요 (클라이언트 단에서 서비스 접속시 발급하여 서버에 보내고 이를 유저와 매핑하여 저장해야함)
    private String targetUserid;
    private String title;
    private String body;

}
