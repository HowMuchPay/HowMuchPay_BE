package com.example.howmuch.service.fcm;

import com.example.howmuch.domain.entity.User;
import com.example.howmuch.domain.repository.UserRepository;
import com.example.howmuch.dto.fcm.FcmNotificationRequestDto;
import com.example.howmuch.dto.fcm.FcmNotificationRequestListDto;
import com.example.howmuch.dto.fcm.FcmNotificationResponseDto;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FcmNotificationService {
    private final FirebaseMessaging firebaseMessaging;
    private final UserRepository userRepository;

    // 그룹으로 알림 메시지 전송
    @Transactional(readOnly = true)
    public FcmNotificationResponseDto sendNotificationToGroup(FcmNotificationRequestListDto requestDto) {
        List<String> validUserPhoneNumber = new ArrayList<>();
        List<String> invalidUserPhoneNumber = new ArrayList<>();

        for (String phoneNumber : requestDto.getTargetUserPhoneNumber()) {
            sendNotification(phoneNumber, requestDto.getTitle(), requestDto.getBody(), validUserPhoneNumber, invalidUserPhoneNumber);
        }

        return buildResponse(validUserPhoneNumber, invalidUserPhoneNumber, requestDto.getTargetUserPhoneNumber());
    }

    // 1 대 1 으로
    @Transactional(readOnly = true)
    public FcmNotificationResponseDto sendNotificationByToken(FcmNotificationRequestDto requestDto) {
        String phoneNumber = requestDto.getTargetUserPhoneNumber();
        sendNotification(phoneNumber, requestDto.getTitle(), requestDto.getBody());
        return buildResponse(phoneNumber);
    }

    private void sendNotification(String phoneNumber, String title, String body, List<String> validUserPhoneNumber, List<String> invalidUserPhoneNumber) {
        try {
            Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                String firebaseToken = user.getFirebaseToken().getToken();
                if (firebaseToken != null) {
                    Notification notification = Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build();

                    Message message = Message.builder()
                            .setToken(firebaseToken)
                            .setNotification(notification)
                            .build();

                    firebaseMessaging.send(message);
                    log.info("알림을 성공적으로 전달하였습니다. targetUserId= {}", phoneNumber);
                    validUserPhoneNumber.add(phoneNumber);
                } else {
                    invalidUserPhoneNumber.add(phoneNumber);
                }
            } else {
                invalidUserPhoneNumber.add(phoneNumber);
            }
        } catch (FirebaseMessagingException e) {
            log.error("알림 보내기를 실패하였습니다. targetUserId={}", phoneNumber, e);
            invalidUserPhoneNumber.add(phoneNumber);
        }
    }

    private void sendNotification(String phoneNumber, String title, String body) {
        sendNotification(phoneNumber, title, body, new ArrayList<>(), new ArrayList<>());
    }

    private FcmNotificationResponseDto buildResponse(List<String> validUserPhoneNumber, List<String> invalidUserPhoneNumber, List<String> targetUserPhoneNumber) {
        FcmNotificationResponseDto responseDto = new FcmNotificationResponseDto();

        if (validUserPhoneNumber.isEmpty()) {
            log.info("모든 유저가 유효하지 않습니다. targetUserIds: {}", targetUserPhoneNumber);
            responseDto.setResponseMessage("모든 유저가 유효하지 않습니다. targetUserIds: " + targetUserPhoneNumber);
        } else {
            log.info("알림을 성공적으로 전달하였습니다. 유효한 유저 수: {}", validUserPhoneNumber.size());
            log.info("유효하지 않은 유저 수: {}", invalidUserPhoneNumber.size());
            responseDto.setResponseMessage("알림을 성공적으로 전달하였습니다. 유효한 유저 수: " + validUserPhoneNumber.size());
        }
        return responseDto;
    }

    private FcmNotificationResponseDto buildResponse(String phoneNumber) {
        FcmNotificationResponseDto responseDto = new FcmNotificationResponseDto();

        log.info("알림을 성공적으로 전달하였습니다. targetUserId= {}", phoneNumber);
        responseDto.setResponseMessage("알림을 성공적으로 전달하였습니다. targetUserId= " + phoneNumber);

        return responseDto;
    }
}


