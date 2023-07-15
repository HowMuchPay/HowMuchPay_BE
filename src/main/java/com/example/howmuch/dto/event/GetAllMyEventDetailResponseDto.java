package com.example.howmuch.dto.event;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllMyEventDetailResponseDto {
    private String acquaintanceNickname;
    private Long receiveAmount;
}
