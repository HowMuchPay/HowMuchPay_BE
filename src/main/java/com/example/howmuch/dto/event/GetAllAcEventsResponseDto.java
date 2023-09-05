package com.example.howmuch.dto.event;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllAcEventsResponseDto {

    private Long totalPayAmount;

    private Map<String, List<GetAllAcEventsResponse>> allAcEvents;
}

