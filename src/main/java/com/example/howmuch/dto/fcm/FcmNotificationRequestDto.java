package com.example.howmuch.dto.fcm;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FcmNotificationRequestDto {
    private Long acId; // 추가된 acId 필드
    private String targetUserPhoneNumber;
    private String title;
    private String body;
}
