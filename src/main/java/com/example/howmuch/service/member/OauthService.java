package com.example.howmuch.service.member;

import com.example.howmuch.contant.Token;
import com.example.howmuch.contant.UserStatus;
import com.example.howmuch.domain.entity.User;
import com.example.howmuch.domain.repository.UserRepository;
import com.example.howmuch.dto.member.ActiveUserOauthResponseDto;
import com.example.howmuch.dto.member.NeedDataUserOauthResponseDto;
import com.example.howmuch.dto.member.OauthTokenResponseDto;
import com.example.howmuch.dto.member.userInfo.KakaoOauthUserInfo;
import com.example.howmuch.util.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
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

    /* providerName = kakao, code = Authorization code */
    @Transactional
    public User getOauth(String providerName, String code) {

        ClientRegistration provider
                = this.inMemoryClientRegistrationRepository.findByRegistrationId(providerName.toLowerCase());
        OauthTokenResponseDto responseDto = getToken(provider, code);
        return saveUserWithUserInfo(providerName.toLowerCase(), responseDto, provider);
    }

    /* 1. Kakao Authorization Server 로 부터 Access Token 받아오기 */
    private OauthTokenResponseDto getToken(ClientRegistration provider, String code) {
        return WebClient.create()
                .post()
                .uri(provider.getProviderDetails().getTokenUri())
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(tokenRequest(provider, code))
                .retrieve()
                .bodyToMono(OauthTokenResponseDto.class)
                .block();
    }

    /* 2. token 요청 uri 생성 -> https://kauth.kakao.com/oauth/token? ~ */
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
    private User saveUserWithUserInfo(String providerName, OauthTokenResponseDto responseDto,
                                      ClientRegistration provider) {
        Map<String, Object> attributes = getUserAttributes(provider, responseDto);

        KakaoOauthUserInfo oauthUserInfo = new KakaoOauthUserInfo(attributes);
        String oauthNickName = oauthUserInfo.getNickName();
        String oauthName = null;
        String oauthProvider = oauthUserInfo.getProvider(); // kakao
        String oauthProviderId = oauthUserInfo.getProviderId(); // oauthId
        String profileImage = oauthUserInfo.getImageUrl(); // profileImage
        Optional<User> optionalUser = this.userRepository.findByOauthId(oauthProviderId);

        return optionalUser.orElseGet(() -> this.userRepository.save(User.builder()
                .name(oauthName)
                .nickname(oauthNickName)
                .userStatus(UserStatus.NEED_DATA)
                .oauthId(oauthProviderId)
                .profileImage(profileImage)
                .build()
        ));
    }

    /* 4. 발급 받은 access token 을 이용해 user attributes 요청 */
    private Map<String, Object> getUserAttributes(ClientRegistration provider, OauthTokenResponseDto responseDto) {
        return WebClient.create()
                .get()
                .uri(provider.getProviderDetails().getUserInfoEndpoint().getUri())
                .headers(header -> header.setBearerAuth(responseDto.getAccessToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();
    }

    /* 5. 처음 로그인한 회원(UserStatus = NEED_DATA) */
    public NeedDataUserOauthResponseDto needDataResult(User user) {
        return NeedDataUserOauthResponseDto.builder()
                .nickName(user.getNickname())
                .name(user.getName()) // null
                .oauthId(user.getOauthId())
                .build();
    }

    /* 6. 이미 로그인한 이력이 있는 회원(UserStatus = Active) */
    public ActiveUserOauthResponseDto oauthLoginResult(User user) {
        return getActiveUserLoginResponseDto(user);
    }

    private ActiveUserOauthResponseDto getActiveUserLoginResponseDto(User user) {
        Token accessToken = this.jwtService.createAccessToken(String.valueOf(user.getId()));
        Token refreshToken = this.jwtService.createRefreshToken();


        return ActiveUserOauthResponseDto.builder()
                .tokenType(BEARER_TYPE)
                .accessToken(accessToken.getTokenValue())
                .expiredTime(accessToken.getExpiredTime())
                .refreshToken(refreshToken.getTokenValue())
                .build();

    }
}
