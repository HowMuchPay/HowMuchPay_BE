package com.example.howmuch.controller;


import com.example.howmuch.dto.user.login.UserInfoLoginRequestDto;
import com.example.howmuch.dto.user.login.UserOauthLoginResponseDto;
import com.example.howmuch.service.user.KakaoOauthService;
import com.example.howmuch.service.user.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
@RestController
public class KakaoOauthController {

    private final OauthService oauthService;
    private final KakaoOauthService kakaoOauthService;

    /* Deprecated */
//    @GetMapping("/login/callback/{provider}")
//    public ResponseEntity<UserOauthLoginResponseDto> oauthLogin(
//        @PathVariable String provider,
//        @RequestParam String code
//    ) throws IOException {
//        User user = this.oauthService.getOauth(provider, code);
//        return new ResponseEntity<>(this.oauthService.oauthLoginResult(user), HttpStatus.OK);
//    }

    @PostMapping("/login/kakao")
    public ResponseEntity<UserOauthLoginResponseDto> kakaoLogin(
            @RequestBody UserInfoLoginRequestDto userInfoLoginRequestDto
    ) throws IOException {
        return new ResponseEntity<>(
                this.kakaoOauthService.getOauth(userInfoLoginRequestDto), HttpStatus.OK
        );
    }
}

