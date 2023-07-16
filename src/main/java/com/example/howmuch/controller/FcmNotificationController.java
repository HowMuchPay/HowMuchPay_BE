package com.example.howmuch.controller;


import com.example.howmuch.dto.fcm.FcmNotificationRequestDto;
import com.example.howmuch.dto.fcm.FcmNotificationRequestListDto;
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
    // 승현 -> : 연락처 필드를 추가하여 findByPhoneNum으로 oauthId를 반환하는 API를 설계하면 어떨까요??
    @PostMapping("/send")
    public String sendNotificationByToken(@RequestBody FcmNotificationRequestDto requestDto) {
        return fcmNotificationService.sendNotificationByToken(requestDto);
    }

    @PostMapping("/sends")
    public String sendNotificationToGroupByToken(@RequestBody FcmNotificationRequestListDto requestListDto) {
        return fcmNotificationService.sendNotificationToGroup(requestListDto);
    }
}
