package com.example.howmuch.service.fcm;

import com.example.howmuch.domain.entity.User;
import com.example.howmuch.domain.repository.UserRepository;
import com.example.howmuch.dto.fcm.FcmNotificationRequestDto;
import com.example.howmuch.dto.fcm.FcmNotificationRequestListDto;
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

    //그룹으로 알림 메시지 전송
    @Transactional(readOnly = true)
    public String sendNotificationToGroup(FcmNotificationRequestListDto requestDto) {
        List<String> targetUserIds = requestDto.getTargetUserIds();

        List<String> validUserIds = new ArrayList<>();
        List<String> invalidUserIds = new ArrayList<>();

        for (String userId : targetUserIds) {
            Optional<User> optionalUser = userRepository.findByOauthId(userId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                String firebaseToken = user.getFirebaseToken().getToken();
                if (firebaseToken != null) {
                    validUserIds.add(userId);
                    Notification notification = Notification.builder()
                            .setTitle(requestDto.getTitle())
                            .setBody(requestDto.getBody())
                            .build();

                    Message message = Message.builder()
                            .setToken(firebaseToken)
                            .setNotification(notification)
                            .build();

                    try {
                        firebaseMessaging.send(message);
                        log.info("알림을 성공적으로 전달하였습니다. targetUserId= {}", userId);
                    } catch (FirebaseMessagingException e) {
                        log.error("알림 보내기를 실패하였습니다. targetUserId={}", userId, e);
                    }
                } else {
                    invalidUserIds.add(userId);
                }
            } else {
                invalidUserIds.add(userId);
            }
        }

        if (validUserIds.isEmpty()) {
            log.info("모든 유저가 유효하지 않습니다. targetUserIds: {}", targetUserIds);
            return "모든 유저가 유효하지 않습니다. targetUserIds: " + targetUserIds;
        }

        log.info("알림을 성공적으로 전달하였습니다. 유효한 유저 수: {}", validUserIds.size());
        log.info("유효하지 않은 유저 수: {}", invalidUserIds.size());
        return "알림을 성공적으로 전달하였습니다. 유효한 유저 수: " + validUserIds.size();
    }


    // 1 대 1 으로
    @Transactional(readOnly = true)
    public String sendNotificationByToken(FcmNotificationRequestDto requestDto) {
        Optional<User> optionalUser
                = userRepository.findByOauthId(requestDto.getTargetUserId()); // 상대방 user

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String firebaseToken = user.getFirebaseToken().getToken();

            if (firebaseToken != null) {
                Notification notification = Notification.builder()
                        .setTitle(requestDto.getTitle())
                        .setBody(requestDto.getBody())
                        .build();

                Message message = Message.builder()
                        .setToken(firebaseToken)
                        .setNotification(notification)
                        .build();

                try {
                    firebaseMessaging.send(message);
                    log.info("알림을 성공적으로 전달하였습니다. targetUserId= {}", requestDto.getTargetUserId());
                    return "알림을 성공적으로 전달하였습니다. targetUserId= " + requestDto.getTargetUserId();
                } catch (FirebaseMessagingException e) {
                    log.error("알림 보내기를 실패하였습니다. targetUserId={}", requestDto.getTargetUserId(), e);
                    return "알림 보내기를 실패하였습니다. targetUserId" + requestDto.getTargetUserId();
                }
            } else {
                log.info("서버에 저장된 해당 유저의 토큰이 존재하지 않습니다. targetUserId={}", requestDto.getTargetUserId());
                return "서버에 저장된 해당 유저의 토큰이 존재하지 않습니다." + requestDto.getTargetUserId();
            }
        } else {
            log.info("해당 유저가 존재하지 않습니다. targetUserId={}", requestDto.getTargetUserId());
            return "해당 유저가 존재하지 않습니다." + requestDto.getTargetUserId();
        }
    }
}