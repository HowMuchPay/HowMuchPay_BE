package com.example.howmuch.dto.event;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetMyEventInfoResponseDto {
    private String myEventDisplayName;
    private LocalDate eventAt;
    private String eventTime;
    private long remainedDay;
}
