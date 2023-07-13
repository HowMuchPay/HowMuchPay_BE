package com.example.howmuch.dto.event;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllMyEventsResponseDto {

    private Long totalReceiveAmount;

    private List<GetAllMyEventsResponse> allMyEvents;
}
