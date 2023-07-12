package com.example.howmuch.dto.member;

import lombok.*;
import org.codehaus.jackson.annotate.JsonProperty;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OauthTokenResponseDto {
    @JsonProperty("access_token")
    private String accessToken;

    private String scope;

    @JsonProperty("token_type")
    private String tokenType;
}
