package com.example.howmuch.dto.user;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowMainResponseDto {

    private long userTotalPayAmount;
    private long userTotalReceiveAmount;
    private List<GetAcEventsInMainResponseDto> lastAcThreeEvents; // 지인 경조사 최신 3개
    private GetAcEventsInMainResponseDto mostLastAcEvents; // 지인 경조사 가장 최신 1개
}
