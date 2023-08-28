package com.example.howmuch.dto.fcm;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FcmNotificationResponseDto {
    private String responseMessage; // "알림을 성공적으로 전달하였습니다."
    private Long acId; // acId 리턴을 통한 상세 페이지 조회
}