package com.example.howmuch.dto.event;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllAcEventsResponseDto {

    private Long totalPayAmount;

    private List<GetAllAcEventsResponse> allAcEvents;
}
