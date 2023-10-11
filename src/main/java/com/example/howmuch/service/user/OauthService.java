package com.example.howmuch.service.user;


import com.example.howmuch.config.security.Token;
import com.example.howmuch.constant.RoleType;
import com.example.howmuch.domain.entity.User;
import com.example.howmuch.domain.repository.UserRepository;
import com.example.howmuch.dto.user.TokenFromOauthServer;
import com.example.howmuch.dto.user.info.KakaoOauthUserInfo;
import com.example.howmuch.dto.user.login.UserOauthLoginResponseDto;
import com.example.howmuch.service.s3.S3Service;
import com.example.howmuch.util.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OauthService {

    private static final String BEARER_TYPE = "Bearer";

    private final InMemoryClientRegistrationRepository inMemoryClientRegistrationRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final S3Service s3Service;


    /* providerName = kakao, code = Authorization code */

    /**
     * Spring security 에서는 어플리케이션 실행 시 yml 에 기재되어 있는 Oauth 설정 값들을 Oauth2ClientProperties 빈으로 등록
     * Oauth2ClientProperties 는 내부 값들을 통해 각 OAuth2 서버 별로 ClientRegistration 이라는 객체를 만들어
     * InMemoryClientRegistrationRepository 에 저장
     */
    @Transactional
    public User getOauth(String providerName, String code) throws IOException {

        ClientRegistration provider
                = this.inMemoryClientRegistrationRepository.findByRegistrationId(providerName.toLowerCase());
        TokenFromOauthServer token = getTokenFromOauthServer(provider, code);
        return saveUserWithUserInfo(token, provider);
    }

    /* 1. Kakao Authorization Server 로 부터 Access Token 받아오기 */
    private TokenFromOauthServer getTokenFromOauthServer(ClientRegistration provider, String code) {
        // https://kauth.kakao.com/oauth/token
        // content-type : application/x-www-urlencoded
        return WebClient.create()
                .post()
                .uri(provider.getProviderDetails().getTokenUri())
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(tokenRequest(provider, code))
                .retrieve()
                .bodyToMono(TokenFromOauthServer.class)
                .block();
    }

    /* 2. kakao server 로 token 요청 uri 생성 -> https://kauth.kakao.com/oauth/token? ~ */
    private MultiValueMap<String, String> tokenRequest(ClientRegistration provider, String code) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", code);
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", provider.getRedirectUri());
        formData.add("client_secret", provider.getClientSecret());
        formData.add("client_id", provider.getClientId());
        return formData;
    }

    /* 3. Oauth 로부터 받아온 회원 정보 회원 테이블 저장 */
    private User saveUserWithUserInfo(TokenFromOauthServer token,
                                      ClientRegistration provider) throws IOException {
        Map<String, Object> attributes = getUserAttributes(provider, token);
        KakaoOauthUserInfo oauthUserInfo = new KakaoOauthUserInfo(attributes);
        String oauthNickName = oauthUserInfo.getNickName(); // nickName
        String oauthId = oauthUserInfo.getProviderId(); // oauthId
        String profileImage = oauthUserInfo.getImageUrl(); // profileImage

        Optional<User> optionalUser = this.userRepository.findByOauthId(oauthId);
        MultipartFile multipartFile = convertImageURLToMultipartFile(profileImage);
        String imageUrl = s3Service.saveFile(multipartFile);
        return optionalUser.orElseGet(() -> this.userRepository.save(User.builder()
                .oauthId(oauthId)
                .nickname(oauthNickName)
                .profileImage(imageUrl)
                .roleType(RoleType.ROLE_USER)
                .userTotalPayAmount(0L)
                .userTotalReceiveAmount(0L)
                .build()));
    }

    /* 4. 발급 받은 access token 을 이용해 user attributes 요청 이때 " provider 의 user-info-uri 로 요청 with token " */
    private Map<String, Object> getUserAttributes(ClientRegistration provider, TokenFromOauthServer token) {
        return WebClient.create()
                .get()
                .uri(provider.getProviderDetails().getUserInfoEndpoint().getUri())
                .headers(header -> header.setBearerAuth(token.getAccessToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();
    }

    /* 5. 소셜 로그인 정보 기반으로 회원 저장 후에 로그인 결과를 반환하는 메소드 */
    @Transactional
    public UserOauthLoginResponseDto oauthLoginResult(User user) {
        return getOauthLoginResult(user);
    }

    private UserOauthLoginResponseDto getOauthLoginResult(User user) {

        // user id(long) -> String
        Token accessToken = this.jwtService.createAccessToken(String.valueOf(user.getId()));
        Token refreshToken = this.jwtService.createRefreshToken();
        LocalDateTime expireTime = LocalDateTime.now()
                .plusSeconds(accessToken.getExpiredTime() / 1000);
        user.setRefreshToken(refreshToken.getTokenValue());
        return UserOauthLoginResponseDto.builder()
                .tokenType(BEARER_TYPE)
                .accessToken(BEARER_TYPE + " " + accessToken.getTokenValue())
                .expiredTime(expireTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))) // 만료 Local Date Time
                .refreshToken(refreshToken.getTokenValue())
                .roleType(user.getRoleType().name())
                .phoneNumber(user.getPhoneNumber())
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
