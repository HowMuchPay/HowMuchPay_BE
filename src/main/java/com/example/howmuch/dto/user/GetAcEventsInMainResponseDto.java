package com.example.howmuch.dto.user;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAcEventsInMainResponseDto {

    private long payAmount;

    private LocalDate eventAt;
}
