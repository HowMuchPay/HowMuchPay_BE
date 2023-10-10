package com.example.howmuch.dto.fcm;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FcmNotificationRequestDto {
    private String targetUserPhoneNumber;
    private String title;
    private String body;
}
