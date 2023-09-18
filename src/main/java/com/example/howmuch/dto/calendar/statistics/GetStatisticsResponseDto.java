package com.example.howmuch.dto.calendar.statistics;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetStatisticsResponseDto {
    private long totalPayment;
    private long totalReceiveAmount;
    private String mostEventCategory;
    private List<StatisticsListResponse> statisticsListResponse;
}
