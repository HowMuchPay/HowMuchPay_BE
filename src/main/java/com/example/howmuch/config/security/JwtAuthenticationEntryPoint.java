//package com.example.howmuch.config.security;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Slf4j
//@Component
//public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
//
//    /*
//        JwtAuthenticationFilter 에서 인증 되지 않은 요청 처리 -> 예외 처리 로직 구현(commence)
//     */
//    @Override
//    public void commence(HttpServletRequest request,
//                         HttpServletResponse response,
//                         AuthenticationException authException) throws IOException, ServletException {
//        log.error("JWT Exception Occurred!");
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UnAuthorized");
//    }
//}
