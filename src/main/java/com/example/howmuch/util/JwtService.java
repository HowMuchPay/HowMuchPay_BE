package com.example.howmuch.util;


import com.example.howmuch.config.security.Token;
import com.example.howmuch.exception.user.UnauthorizedUserException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtService {

    @Value("e${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private Long accessTokenExpiration;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpiration;

    // accessToken
    public Token createAccessToken(String payLoad) {
        String token = createToken(payLoad, accessTokenExpiration);
        return getToken(token, accessTokenExpiration);
    }

    // refreshToken
    public Token createRefreshToken() {
        String token = createToken(UUID.randomUUID().toString(), refreshTokenExpiration);
        return getToken(token, refreshTokenExpiration);
    }

    private String createToken(String payLoad, Long tokenExpiration) {
        Claims claims = Jwts.claims().setSubject(payLoad);
        Date accessTokenExpiresIn = new Date(new Date().getTime() + accessTokenExpiration);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(accessTokenExpiresIn)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    private Token getToken(String token, Long expiration) {
        return Token.builder()
                .tokenValue(token)
                .expiredTime(expiration)
                .build();
    }

    public String getPayLoad(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        } catch (JwtException e) {
            throw new UnauthorizedUserException("로그인이 필요합니다.");
        }
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            log.warn("만료된 JWT 입니다.");
        } catch (UnsupportedJwtException e) {
            log.warn("지원되지 않는 JWT 입니다.");
        } catch (IllegalArgumentException e) {
            log.warn("JWT 에 오류가 존재합니다.");
        }
        return false;
    }

}
