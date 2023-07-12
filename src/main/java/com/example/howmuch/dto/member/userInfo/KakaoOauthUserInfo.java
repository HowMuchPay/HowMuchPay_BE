package com.example.howmuch.dto.member.userInfo;

import java.util.Map;


public class KakaoOauthUserInfo implements OauthUserInfo {

    private final Map<String, Object> attributes;

    public KakaoOauthUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }


    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("id"));
    }

    public String getNickName() {
        return (String) getProfile().get("nickname");
    }

    public String getImageUrl() {
        return (String) getProfile().get("thumbnail_image_url");
    }

    public Map<String, Object> getKakaoAccount() {
        return (Map<String, Object>) attributes.get("kakao_account");
    }

    public Map<String, Object> getProfile() {
        return (Map<String, Object>) getKakaoAccount().get("profile");
    }

}
