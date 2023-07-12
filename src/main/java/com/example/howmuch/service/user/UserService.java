package com.example.howmuch.service.user;


import com.example.howmuch.domain.entity.User;
import com.example.howmuch.domain.repository.UserRepository;
import com.example.howmuch.exception.user.NotFoundUserException;
import com.example.howmuch.exception.user.NotMatchUserException;
import com.example.howmuch.util.RedisUtil;
import com.example.howmuch.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RedisUtil redisUtil;


    /**
     * 회원 탈퇴를 한다는 건
     * 1. Redis 데이터 삭제
     * 2. User DB 에서 User 삭제
     */

    @Transactional
    public void deleteUser() {
        User user = findUserFromToken();
        this.redisUtil.deleteData(String.valueOf(user.getId()));
        this.userRepository.delete(user);
    }

    public User findUserFromToken() {
        return this.userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new NotMatchUserException("토큰 정보와 일치하는 회원 정보가 없습니다."));
    }

    public User findById(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new NotFoundUserException("일치하는 회원 정보를 찾을 수 없습니다."));
    }

}
