package com.example.howmuch.controller;


import com.example.howmuch.domain.entity.User;
import com.example.howmuch.dto.user.UserOauthLoginResponseDto;
import com.example.howmuch.service.user.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
public class KakaoOauthController {

    private final OauthService oauthService;

    @GetMapping("/login/callback/{provider}")
    public ResponseEntity<UserOauthLoginResponseDto> oauthLogin(
            @PathVariable String provider,
            @RequestParam String code
    ) {
        // oauth 로그인한 회원
        User user = this.oauthService.getOauth(provider, code);
        return new ResponseEntity<>(this.oauthService.oauthLoginResult(user), HttpStatus.OK);
    }
}

