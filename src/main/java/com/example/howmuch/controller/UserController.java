package com.example.howmuch.controller;

import com.example.howmuch.dto.user.NewAccessTokenResponseDto;
import com.example.howmuch.service.user.AuthService;
import com.example.howmuch.service.user.UserService;
import com.example.howmuch.util.AuthTransformUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    // access token 재발급 해주는 메소드
    @PostMapping("/reissue")
    public ResponseEntity<NewAccessTokenResponseDto> updateAccessToken(
            HttpServletRequest request
    ) {
        String accessToken = AuthTransformUtil.resolveAccessTokenFromRequest(request);
        String refreshToken = AuthTransformUtil.resolveRefreshTokenFromRequest(request);
        return new ResponseEntity<>(
                authService.accessTokenByRefreshToken(accessToken, refreshToken), HttpStatus.OK);

    }

    // logout 메소드
    @PostMapping("/logout/me")
    public ResponseEntity<Void> logout(
            HttpServletRequest request
    ) {
        this.authService.logout(request);
        return ResponseEntity.ok().build();
    }

    // 회원 탈퇴 메소드
    @DeleteMapping
    public ResponseEntity<Void> withDrawUser() {
        this.userService.deleteUser();
        return ResponseEntity.ok().build();
    }
}
