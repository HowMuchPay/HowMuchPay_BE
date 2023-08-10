package com.example.howmuch.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GetAllMyEventDetailResponseDto {
    private GetMyEventInfoResponseDto myEventInfo;
    private List<GetAllMyEventDetails> myDetails;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class GetAllMyEventDetails {
        private Long id;
        private String acquaintanceNickname;
        private long receiveAmount;
    }
}
