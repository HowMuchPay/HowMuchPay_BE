package com.example.howmuch.controller;

import com.example.howmuch.service.fcm.FireBaseTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tokens")
@RequiredArgsConstructor
public class FirebaseTokenController {
    private final FireBaseTokenService firebaseTokenService;

    @PostMapping
    public ResponseEntity<Long> saveTokenForUser(@RequestParam String token) {
        return new ResponseEntity<>(
                this.firebaseTokenService.saveTokenForUser(token), HttpStatus.OK);
    }
}
// 알림을 보낼려면 (token) from device 로그인시 ->
// 프론트에서 fcm token (기기 고유 번호) -> userId(oauth Id) 를 통해 상대방의 fcm token 정보 알아서 전송