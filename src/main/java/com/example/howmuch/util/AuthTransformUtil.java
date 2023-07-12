package com.example.howmuch.util;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class AuthTransformUtil {

    // Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
    public static final String ACCESS_TOKEN_HEADER = "Authorization";

    // Refresh-Token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
    public static final String REFRESH_TOKEN_HEADER = "Refresh-Token";

    public static String BEARER_TYPE = "Bearer";


    /* Request 로부터 AccessToken Extraction */
    public static String resolveAccessTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(ACCESS_TOKEN_HEADER);

        if (!ObjectUtils.isEmpty(token) && token.toLowerCase().startsWith(BEARER_TYPE.toLowerCase())) {
            return token.substring(BEARER_TYPE.length()).trim();
        }
        return null;
    }

    /* Request 로부터 RefreshToken Extraction */
    public static String resolveRefreshTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(REFRESH_TOKEN_HEADER);

        if (!ObjectUtils.isEmpty(token)) {
            return token;
        }
        return null;
    }
}
