package com.example.howmuch.service.user;


import com.example.howmuch.domain.entity.AcEvent;
import com.example.howmuch.domain.entity.User;
import com.example.howmuch.domain.repository.AcEventRepository;
import com.example.howmuch.domain.repository.UserRepository;
import com.example.howmuch.dto.home.HomeResponseDto;
import com.example.howmuch.exception.user.NotFoundUserException;
import com.example.howmuch.exception.user.NotMatchUserException;
import com.example.howmuch.exception.user.PhoneNumberAlreadyExistsException;
import com.example.howmuch.util.RedisUtil;
import com.example.howmuch.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RedisUtil redisUtil;
    private final AcEventRepository acEventRepository;


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

    @Transactional(readOnly = true)
    public User findUserFromToken() {
        return this.userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new NotMatchUserException("토큰 정보와 일치하는 회원 정보가 없습니다."));
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new NotFoundUserException("일치하는 회원 정보를 찾을 수 없습니다."));
    }

    @Transactional
    public void addUserPhoneNumber(String phone) {
        User user = findUserFromToken();
        if (isPhoneNumberExists(phone)) {
            throw new PhoneNumberAlreadyExistsException("이미 등록된 전화번호 입니다");
        }
        user.addPhoneNumber(phone);
    }

    @Transactional(readOnly = true)
    public HomeResponseDto getHome() {
        User user = findUserFromToken();

        log.info(user.toString());
        AcEvent closestEvent = findClosestEvent(user);
        log.info(closestEvent.toString());

        long pay = user.getUserTotalPayAmount();
        long receive = user.getUserTotalReceiveAmount();

        int payPercentage = calculatePayPercentage(pay, receive);

        LocalDate currentDate = LocalDate.now();
        int daysUntilEvent = (int) ChronoUnit.DAYS.between(currentDate, closestEvent.getEventAt());

        return HomeResponseDto.builder()
                .userTotalPayAmount(pay)
                .userTotalReceiveAmount(receive)
                .payPercentage(payPercentage)
                .nickname(closestEvent.getAcquaintanceNickname())
                .eventCategory(closestEvent.getEventCategory().getValue())
                .dDay(daysUntilEvent)
                .build();
    }

    private int calculatePayPercentage(long totalPayAmount, long totalReceiveAmount) {
        if (totalPayAmount + totalReceiveAmount == 0) {
            return 0; // 분모가 0인 경우 방지
        }
        return (int) (totalPayAmount / (totalPayAmount + totalReceiveAmount) * 100);
    }

    private AcEvent findClosestEvent(User user) {
        return acEventRepository.findFirstByUserAndEventAtGreaterThanOrderByEventAtAsc(user, LocalDate.now()).orElse(null);
    }

    private boolean isPhoneNumberExists(String phone) {
        return userRepository.existsByPhoneNumber(phone);
    }
}
