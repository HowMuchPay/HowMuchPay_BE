package com.example.howmuch.controller;


import com.example.howmuch.dto.fcm.FcmNotificationRequestDto;
import com.example.howmuch.service.fcm.FcmNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/notification")
@RestController
public class FcmNotificationController {

    private final FcmNotificationService fcmNotificationService;

    // 동근 궁금 : 상대방 oauthId 를 어떻게 프론트에서 가져오나?
    @PostMapping("/send")
    public String sendNotificationByToken(@RequestBody FcmNotificationRequestDto requestDto) {
        return fcmNotificationService.sendNotificationByToken(requestDto);
    }
}
