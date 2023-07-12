//package com.example.howmuch.config.security;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@RequiredArgsConstructor
//@EnableWebSecurity(debug = true)
//public class SecurityConfig {
//
//    private final JwtProvider jwtProvider;
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .csrf(
//                        csrf -> csrf.disable()
//                )
//                .authorizeRequests(
//                        authroizeRequests -> authroizeRequests
//                                .antMatchers("/**")
//                                .permitAll()
//                )
//                .formLogin(
//                        formLogin -> formLogin
//                                .loginPage("/member/login") // GET
//                                .loginProcessingUrl("/member/login")
//                )
//                .oauth2Login(
//                        oauth2Login -> oauth2Login
//                                .loginPage("/member/login")
//                                .userInfoEndpoint(
//                                        userInfoEndpoint -> userInfoEndpoint
//                                                .userService(oauth2UserService)
//                                )
//                ).logout(logout -> logout
//                        .logoutUrl("/member/logout")
//                );
//        return httpSecurity.build();
//    }
//}
