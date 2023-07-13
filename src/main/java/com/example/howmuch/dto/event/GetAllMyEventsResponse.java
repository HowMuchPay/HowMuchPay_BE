package com.example.howmuch.dto.event;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllMyEventsResponse {

    private LocalDate eventAt;

    private Long receiveAmount;

    private Integer eventCategory;
}
