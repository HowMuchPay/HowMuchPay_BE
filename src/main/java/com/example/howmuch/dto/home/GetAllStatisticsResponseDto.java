package com.example.howmuch.dto.home;

import com.example.howmuch.dto.event.GetAllAcEventsResponse;
import com.example.howmuch.dto.event.GetAllStatisticsMyEventDetailResponseDto;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllStatisticsResponseDto {

    private List<GetAllStatisticsMyEventDetailResponseDto> allMyEvents;
    private Map<String, List<GetAllAcEventsResponse>> allAcEvents;

}
