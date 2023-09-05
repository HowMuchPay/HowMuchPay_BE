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

//    .toList()
//                .stream()
//                .map(MyEvent::toGetAllByEventsResponse)
//                .collect(Collectors.groupingBy(
//                        response -> {
//                            return YearMonth.from(response.getEventAt()).toString();
//                        }
//                ));
//        return new GetAllMyEventsResponseDto(getUser().getUserTotalPayAmount(), allMyEvents);
