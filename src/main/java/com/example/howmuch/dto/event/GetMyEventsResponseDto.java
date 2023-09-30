package com.example.howmuch.dto.event;

import com.example.howmuch.domain.entity.MyEvent;
import java.time.LocalDate;
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
public class GetMyEventsResponseDto {

    private Long id;
    private String myEventDisplayName;
    private LocalDate eventAt;
    private Integer eventCategory;

    public static GetMyEventsResponseDto from(MyEvent myEvent) {
        String myEventDisplayName = myEvent.getMyEventName();
        if (myEventDisplayName == null) {
            myEventDisplayName =
                myEvent.getMyEventCharacterName() + "의 " + myEvent.getEventCategory()
                    .getCategoryName();
        } else {
            myEventDisplayName = myEvent.getMyEventCharacterName() + "의 " + myEventDisplayName;
        }
        return GetMyEventsResponseDto.builder()
            .id(myEvent.getId())
            .eventAt(myEvent.getEventAt())
            .eventCategory(myEvent.getEventCategory().getValue())
            .myEventDisplayName(myEventDisplayName)
            .build();
    }

}
