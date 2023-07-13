package com.example.howmuch.controller;

import com.example.howmuch.service.fcm.FireBaseTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tokens")
@RequiredArgsConstructor
public class FirebaseTokenController {
    private final FireBaseTokenService firebaseTokenService;

    @PostMapping("/{userId}")
    public ResponseEntity<String> saveTokenForUser(@PathVariable Long userId, @RequestBody String token) {
        firebaseTokenService.saveTokenForUser(userId, token);
        return ResponseEntity.ok("토큰이 해당 유저에게 주입 되었습니다: " + userId);
    }
}