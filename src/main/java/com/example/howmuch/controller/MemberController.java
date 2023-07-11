package com.example.howmuch.controller;

import com.example.howmuch.domain.entity.User;
import com.example.howmuch.service.member.MemberService;
import com.example.howmuch.service.member.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.example.howmuch.contant.UserStatus.NEED_DATA;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;
    private final OauthService oauthService;

    @GetMapping("/login/callback/{provider}")
    public ResponseEntity<?> oauthLogin(
            @PathVariable String provider,
            @RequestParam String code
    ) {
        // oauth 로그인한 회원
        User user = this.oauthService.getOauth(provider, code);
        if (user.getUserStatus() == NEED_DATA) {
            return new ResponseEntity<>(this.oauthService.needDataResult(user), HttpStatus.MOVED_PERMANENTLY);
        } else {
            return new ResponseEntity<>(this.oauthService.oauthLoginResult(user), HttpStatus.OK);
        }
    }
}
