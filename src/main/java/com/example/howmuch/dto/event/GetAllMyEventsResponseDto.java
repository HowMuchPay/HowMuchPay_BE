package com.example.howmuch.dto.event;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllMyEventsResponseDto {

    private Long totalReceiveAmount;

    private Map<String, List<GetAllMyEventsResponse>> allMyEvents;

}
