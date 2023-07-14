package com.example.howmuch.dto.event;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllAcEventsResponse {

    private Integer month;

    private LocalDate eventAt;

    private Long payAmount;

    private Integer eventCategory;
}
