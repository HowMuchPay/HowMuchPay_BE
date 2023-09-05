package com.example.howmuch.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllAcEventsResponse {
    private LocalDate eventAt;
    private Long payAmount;
    private Integer eventCategory;
}
