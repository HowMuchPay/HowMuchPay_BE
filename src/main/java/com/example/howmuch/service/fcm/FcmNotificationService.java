package com.example.howmuch.service.fcm;

import com.example.howmuch.domain.entity.User;
import com.example.howmuch.domain.repository.UserRepository;
import com.example.howmuch.dto.fcm.FcmNotificationRequestDto;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FcmNotificationService {
    private final FirebaseMessaging firebaseMessaging;
    private final UserRepository userRepository;

    public String sendNotificationByToken(FcmNotificationRequestDto requestDto) {
        Optional<User> optionalUser = userRepository.findByOauthId(requestDto.getTargetUserid());

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
                    log.info("알림을 성공적으로 전달하였습니다. targetUserId= {}", requestDto.getTargetUserid());
                    return "알림을 성공적으로 전달하였습니다. targetUserId= " + requestDto.getTargetUserid();
                } catch (FirebaseMessagingException e) {
                    log.error("알림 보내기를 실패하였습니다. targetUserId={}", requestDto.getTargetUserid(), e);
                    return "알림 보내기를 실패하였습니다. targetUserId" + requestDto.getTargetUserid();
                }
            } else {
                log.info("서버에 저장된 해당 유저의 토큰이 존재하지 않습니다. targetUserId={}", requestDto.getTargetUserid());
                return "서버에 저장된 해당 유저의 토큰이 존재하지 않습니다." + requestDto.getTargetUserid();
            }
        } else {
            log.info("해당 유저가 존재하지 않습니다. targetUserId={}", requestDto.getTargetUserid());
            return "해당 유저가 존재하지 않습니다." + requestDto.getTargetUserid();
        }
    }
}