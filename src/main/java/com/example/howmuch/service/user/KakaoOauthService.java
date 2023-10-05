package com.example.howmuch.service.user;

import com.example.howmuch.config.security.Token;
import com.example.howmuch.domain.entity.User;
import com.example.howmuch.domain.repository.UserRepository;
import com.example.howmuch.dto.user.login.UserInfoLoginRequestDto;
import com.example.howmuch.dto.user.login.UserOauthLoginResponseDto;
import com.example.howmuch.service.s3.S3Service;
import com.example.howmuch.util.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoOauthService {

    private static final String BEARER_TYPE = "Bearer";
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final S3Service s3Service;

    @Transactional
    public UserOauthLoginResponseDto getOauth(UserInfoLoginRequestDto userInfoLoginRequestDto)
            throws IOException {
        String profileImage = userInfoLoginRequestDto.getProfileImageUrl();
        MultipartFile multipartFile = convertImageURLToMultipartFile(profileImage);
        String imageUrl = s3Service.saveFile(multipartFile);
        userInfoLoginRequestDto.setProfileImageUrl(imageUrl);
        return processKakaoToken(saveUser(userInfoLoginRequestDto));
    }

    private User saveUser(UserInfoLoginRequestDto userInfoLoginRequestDto) {
        return this.userRepository.findByOauthId(userInfoLoginRequestDto.getOauthId()).orElseGet(()
                -> this.userRepository.save(userInfoLoginRequestDto.toEntity())
        );
    }


    private UserOauthLoginResponseDto processKakaoToken(User user) {
        Token accessToken = this.jwtService.createAccessToken(String.valueOf(user.getId()));
        Token refreshToken = this.jwtService.createRefreshToken();
        LocalDateTime expireTime = LocalDateTime.now()
                .plusSeconds(accessToken.getExpiredTime() / 1000);
        user.setRefreshToken(refreshToken.getTokenValue());
        return UserOauthLoginResponseDto.builder()
                .tokenType(BEARER_TYPE)
                .accessToken(BEARER_TYPE + " " + accessToken.getTokenValue())
                .expiredTime(expireTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .refreshToken(refreshToken.getTokenValue())
                .roleType(user.getRoleType().name())
                .phoneExisted(user.getPhoneNumber() != null)
                .build();
    }

    public MultipartFile convertImageURLToMultipartFile(String imageUrl) throws IOException {
        // URL에서 이미지 다운로드
        try (InputStream inputStream = new URL(imageUrl).openStream()) {
            // 임시 파일로 저장
            Path tempFile = Files.createTempFile("temp", ".jpg");
            Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);

            // 임시 파일을 MultipartFile로 변환
            return new MockMultipartFile(
                    "file",
                    "image.jpg",
                    "image/jpeg",
                    Files.newInputStream(tempFile)
            );
        }
    }
}
