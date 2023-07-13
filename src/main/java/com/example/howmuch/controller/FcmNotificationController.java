package com.example.howmuch.controller;


import com.example.howmuch.dto.user.fcm.FcmNotificationRequestDto;
import com.example.howmuch.service.user.fcm.FcmNotificationService;
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

    @PostMapping("/send")
    public String sendNotificationByToken(@RequestBody FcmNotificationRequestDto requestDto) {
        return fcmNotificationService.sendNotificationByToken(requestDto);
    }

}
