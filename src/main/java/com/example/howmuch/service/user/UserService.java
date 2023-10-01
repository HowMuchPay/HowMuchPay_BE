package com.example.howmuch.service.user;


import com.example.howmuch.domain.entity.AcEvent;
import com.example.howmuch.domain.entity.MyEvent;
import com.example.howmuch.domain.entity.User;
import com.example.howmuch.domain.repository.AcEventRepository;
import com.example.howmuch.domain.repository.MyEventRepository;
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
    private final MyEventRepository myEventRepository;

    /**
     * - /user/delete
     * - DB 해당 유저 삭제
     * - 프론트에서 Access + Refresh 삭제
     **/
    @Transactional
    public void deleteUser() {
        User user = findUserFromToken();
//        this.redisUtil.deleteData(String.valueOf(user.getId()));
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

    /**
     * - /user/phone
     * - User 테이블에 Phone 칼럼 추가
     **/
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
        AcEvent closestAcEvent = findClosestAcEvent(user);
        MyEvent closestMyEvent = findClosestMyEvent(user);

        long pay = user.getUserTotalPayAmount();
        long receive = user.getUserTotalReceiveAmount();
        int payPercentage = calculatePayPercentage(pay, receive);
        int myDay = Math.abs(calculateDaysUntilMyEvent(closestMyEvent));
        int acDay = Math.abs(calculateDaysUntilEvent(closestAcEvent));

        if (closestAcEvent == null && closestMyEvent == null) {
            // 둘 다 null이면 Event 정보를 빈 값으로 처리
            return HomeResponseDto.builder()
                    .userTotalPayAmount(pay)
                    .userTotalReceiveAmount(receive)
                    .payPercentage(payPercentage)
                    .build();
        }
        if (closestAcEvent == null) {
            return closestMyEvent.toHomeResponseDto(pay, receive, payPercentage, myDay,
                    closestMyEvent.getId());
        }
        if (closestMyEvent == null) {
            return closestAcEvent.toHomeResponseDto(pay, receive, payPercentage, acDay,
                    closestAcEvent.getId());
        }
        if (acDay < myDay) {
            return closestAcEvent.toHomeResponseDto(pay, receive, payPercentage, acDay,
                    closestAcEvent.getId());
        } else {
            return closestMyEvent.toHomeResponseDto(pay, receive, payPercentage, myDay,
                    closestMyEvent.getId());

        }
    }

    private int calculateDaysUntilEvent(AcEvent event) {
        if (event == null) {
            return Integer.MAX_VALUE;
        }
        LocalDate currentDate = LocalDate.now();
        return (int) ChronoUnit.DAYS.between(event.getEventAt(), currentDate);
    }

    private int calculateDaysUntilMyEvent(MyEvent event) {
        if (event == null) {
            return Integer.MAX_VALUE;
        }
        LocalDate currentDate = LocalDate.now();
        return (int) ChronoUnit.DAYS.between(event.getEventAt(), currentDate);
    }

    private int calculatePayPercentage(long totalPayAmount, long totalReceiveAmount) {
        if (totalPayAmount + totalReceiveAmount == 0) {
            return 0; // 분모가 0인 경우 방지
        }
        double percentage = (double) totalPayAmount / (totalPayAmount + totalReceiveAmount) * 100;

        // 퍼센트 값을 1부터 100 사이의 값으로 제한
        if (percentage < 1) {
            return 0;
        } else if (percentage > 100) {
            return 100;
        } else {
            return (int) percentage;
        }
    }

    private AcEvent findClosestAcEvent(User user) {
        return acEventRepository.findFirstByUserAndEventAtGreaterThanEqualOrderByEventAtAsc(user,
                LocalDate.now()).orElse(null);
    }

    private MyEvent findClosestMyEvent(User user) {
        return myEventRepository.findFirstByUserAndEventAtGreaterThanEqualOrderByEventAtAsc(user,
                LocalDate.now()).orElse(null);
    }

    private boolean isPhoneNumberExists(String phone) {
        return userRepository.existsByPhoneNumber(phone);
    }
}
