package com.example.howmuch.dto.home;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomeResponseDto {

    private long userTotalPayAmount;

    private long userTotalReceiveAmount;

    private Integer payPercentage;

    private String eventType;

    private String eventDisplayName;

    private Long eventId;

    private Integer eventCategory;

    private Integer dDay;

}

