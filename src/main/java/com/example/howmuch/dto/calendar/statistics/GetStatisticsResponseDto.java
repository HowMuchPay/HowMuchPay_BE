package com.example.howmuch.dto.calendar.statistics;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetStatisticsResponseDto {
    private long totalPayment; // 받은 총 금액(냈어요)
    private long totalReceiveAmount; // 낸 총금액(받았어요)
    private List<String> mostEventCategory; // 가장 많은 비용을 지출한 경조사
    private Long mostEventPayAmount; // 가장 많은 비용을 지출한 경조사 비용
    private Map<String, List<StatisticsListResponse>> statisticsListResponse;
}
