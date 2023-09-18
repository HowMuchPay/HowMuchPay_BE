package com.example.howmuch.dto.event;

import com.example.howmuch.constant.EventCategory;
import com.example.howmuch.domain.entity.MyEventDetail;
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
public class GetAllStatisticsMyEventDetailResponseDto {

    private Long id;
    private String myEventDisplayName;
    private String AcquaintanceNickname;
    private LocalDate eventAt;
    private Long receiveAmount;
    private Integer eventCategory;

    public static GetAllStatisticsMyEventDetailResponseDto from(
        MyEventDetail myEventDetail){
        String myEventName = myEventDetail.getMyEvent().getMyEventName();
        String myEventCharacterName = myEventDetail.getMyEvent().getMyEventCharacterName();
        EventCategory eventCategory = myEventDetail.getMyEvent().getEventCategory();

        String myEventDisplayName;
        if (myEventName == null) {
            myEventDisplayName = myEventCharacterName + "의 " + eventCategory.getCategoryName();
        } else {
            myEventDisplayName = myEventCharacterName + "의 " + myEventName;
        }

        return GetAllStatisticsMyEventDetailResponseDto.builder()
            .id(myEventDetail.getId())
            .myEventDisplayName(myEventDisplayName)
            .AcquaintanceNickname(myEventDetail.getAcquaintanceNickname())
            .eventAt(LocalDate.from(myEventDetail.getCreatedAt()))
            .receiveAmount(myEventDetail.getReceiveAmount())
            .eventCategory(eventCategory.getValue())
            .build();
    }
}
