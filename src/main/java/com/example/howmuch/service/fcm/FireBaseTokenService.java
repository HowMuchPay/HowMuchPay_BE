package com.example.howmuch.service.fcm;

import com.example.howmuch.domain.entity.FirebaseToken;
import com.example.howmuch.domain.entity.User;
import com.example.howmuch.domain.repository.FirebaseTokenRepository;
import com.example.howmuch.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FireBaseTokenService {

    private final FirebaseTokenRepository firebaseTokenRepository;
    private final UserRepository userRepository;


    public void saveTokenForUser(Long userId, String token) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            FirebaseToken firebaseToken = FirebaseToken.builder()
                    .user(user)
                    .token(token)
                    .build();
            firebaseTokenRepository.save(firebaseToken);
            log.info("토큰이 유저에게 성공적으로 저장되었습니다. UserId={}", userId);
        } else {
            log.error("유저를 찾을 수 없습니다. UserId={}", userId);
            throw new UsernameNotFoundException("User not found with id: " + userId);
        }
    }
}


