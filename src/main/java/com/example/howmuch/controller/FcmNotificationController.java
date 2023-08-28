package com.example.howmuch.controller;


import com.example.howmuch.dto.fcm.FcmNotificationRequestDto;
import com.example.howmuch.dto.fcm.FcmNotificationRequestListDto;
import com.example.howmuch.dto.fcm.FcmNotificationResponseDto;
import com.example.howmuch.service.fcm.FcmNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<FcmNotificationResponseDto> sendNotificationByToken(@RequestBody FcmNotificationRequestDto requestDto) {
        return ResponseEntity.ok(fcmNotificationService.sendNotificationByToken(requestDto));
    }

    @PostMapping("/sends")
    public ResponseEntity<FcmNotificationResponseDto> sendNotificationToGroupByToken(@RequestBody FcmNotificationRequestListDto requestListDto) {
        return ResponseEntity.ok(fcmNotificationService.sendNotificationToGroup(requestListDto));
    }
}
