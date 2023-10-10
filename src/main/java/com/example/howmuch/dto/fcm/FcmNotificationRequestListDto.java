package com.example.howmuch.dto.fcm;


import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FcmNotificationRequestListDto {
    private List<String> targetUserPhoneNumber;
    private String title;
    private String body;
}
