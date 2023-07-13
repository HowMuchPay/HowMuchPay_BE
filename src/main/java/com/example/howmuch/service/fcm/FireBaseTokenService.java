package com.example.howmuch.service.fcm;

import com.example.howmuch.domain.entity.FirebaseToken;
import com.example.howmuch.domain.entity.User;
import com.example.howmuch.domain.repository.FirebaseTokenRepository;
import com.example.howmuch.domain.repository.UserRepository;
import com.example.howmuch.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FireBaseTokenService {

    private final FirebaseTokenRepository firebaseTokenRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long saveTokenForUser(String token) {
        Long userId = SecurityUtil.getCurrentUserId();

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("일치하는 회원이 존재하지 않습니다."));

        return this.firebaseTokenRepository.save(FirebaseToken.builder()
                        .user(user)
                        .token(token)
                        .build())
                .getId();
    }
}


