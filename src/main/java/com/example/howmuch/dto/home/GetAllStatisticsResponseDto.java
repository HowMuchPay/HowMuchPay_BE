package com.example.howmuch.dto.home;

import com.example.howmuch.dto.event.GetAllAcEventsResponse;
import com.example.howmuch.dto.event.GetAllMyEventsResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

    private Map<String, List<CombinedEventResponse>> allCombineEvents;

    public static Map<String, List<CombinedEventResponse>> combineAndSortEvents(
        Map<String, List<GetAllMyEventsResponse>> allMyEvents,
        Map<String, List<GetAllAcEventsResponse>> allAcEvents) {
        Map<String, List<CombinedEventResponse>> combinedEvents = new LinkedHashMap<>();

        // 모든 나의 이벤트를 combinedEvents에 추가
        for (Map.Entry<String, List<GetAllMyEventsResponse>> entry : allMyEvents.entrySet()) {
            List<CombinedEventResponse> myEventResponses = entry.getValue().stream()
                .map(response -> new CombinedEventResponse(response.getId(),
                    response.getMyEventDisplayName(), response.getEventAt(),
                    response.getReceiveAmount(), response.getEventCategory(), "MyEvent"))
                .collect(Collectors.toList());

            // 중복된 키가 있을 경우 리스트에 추가
            combinedEvents.computeIfAbsent(entry.getKey(), k -> new ArrayList<>())
                .addAll(myEventResponses);
        }

        // 모든 지인 이벤트를 combinedEvents에 추가
        for (Map.Entry<String, List<GetAllAcEventsResponse>> entry : allAcEvents.entrySet()) {
            List<CombinedEventResponse> acEventResponses = entry.getValue().stream()
                .map(response -> new CombinedEventResponse(response.getId(),
                    response.getAcEventDisplayName(), response.getEventAt(),
                    response.getPayAmount(), response.getEventCategory(), "AcEvent"))
                .collect(Collectors.toList());

            // 중복된 키가 있을 경우 리스트에 추가
            combinedEvents.computeIfAbsent(entry.getKey(), k -> new ArrayList<>())
                .addAll(acEventResponses);

            // combinedEvents 내의 이벤트를 날짜를 기준으로 내림차순으로 정렬
            combinedEvents.forEach((key, value) -> value.sort(
                Comparator.comparing(CombinedEventResponse::getEventAt).reversed()));

        }
        return combinedEvents;
    }
}
