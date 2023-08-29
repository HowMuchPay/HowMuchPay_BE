package com.example.howmuch.dto.Home;

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

    private String nickname;

    private Integer eventCategory;

    private Integer dDay;

}
