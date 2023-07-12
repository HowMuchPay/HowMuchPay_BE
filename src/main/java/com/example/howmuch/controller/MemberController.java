package com.example.howmuch.controller;

import com.example.howmuch.domain.entity.User;
import com.example.howmuch.dto.member.NewAccessTokenResponseDto;
import com.example.howmuch.dto.member.UserOauthLoginResponseDto;
import com.example.howmuch.service.member.AuthService;
import com.example.howmuch.service.member.MemberService;
import com.example.howmuch.service.member.OauthService;
import com.example.howmuch.util.AuthTransformUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;
    private final OauthService oauthService;
    private final AuthService authService;

    @GetMapping("/login/callback/{provider}")
    public ResponseEntity<UserOauthLoginResponseDto> oauthLogin(
            @PathVariable String provider,
            @RequestParam String code
    ) {
        // oauth 로그인한 회원
        User user = this.oauthService.getOauth(provider, code);
        return new ResponseEntity<>(this.oauthService.oauthLoginResult(user), HttpStatus.OK);
    }

    @PostMapping("/reissue")
    public ResponseEntity<NewAccessTokenResponseDto> updateAccessToken(
            HttpServletRequest request
    ) {
        String accessToken = AuthTransformUtil.resolveAccessTokenFromRequest(request);
        String refreshToken = AuthTransformUtil.resolveRefreshTokenFromRequest(request);
        return new ResponseEntity<>(
                authService.accessTokenByRefreshToken(accessToken, refreshToken), HttpStatus.OK);

    }
}
