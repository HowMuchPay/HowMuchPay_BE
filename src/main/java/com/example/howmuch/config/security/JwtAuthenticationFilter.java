package com.example.howmuch.config.security;

import com.example.howmuch.domain.entity.User;
import com.example.howmuch.domain.repository.UserRepository;
import com.example.howmuch.service.user.AuthService;
import com.example.howmuch.util.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "Authorization";
    public static String BEARER_TYPE = "Bearer";
    private final JwtService jwtService;
    private final AuthService authService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getMethod().equals("OPTIONS")) {
            return;
        }
        /**
         * 전달 받은 Access token 부터 Authentication 인증 객체 Security Context에 저장
         */
        try {
            String token = this.resolveTokenFromRequest(request);
            // access token 이 있고 유효하다면
            if (StringUtils.hasText(token) && this.jwtService.validateToken(token)) {
                User user = this.authService.findUserByToken(token);
                UserAuthentication authentication = new UserAuthentication(user);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            // access token 이 만료된 경우 or access token 정보로 식별 안되는 경우
            // 예외가 발생하기만 해도 ExceptionTranslationFilter 호출
        } catch (InsufficientAuthenticationException e) {
            log.info("JwtAuthentication UnauthorizedUserException!");
        }
        filterChain.doFilter(request, response);
    }

    private String resolveTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);
        if (!ObjectUtils.isEmpty(token) && token.toLowerCase().startsWith(BEARER_TYPE.toLowerCase())) {
            return token.substring(BEARER_TYPE.length()).trim();
        }
        return null;
    }
}