package com.example.howmuch.dto.event;

import com.example.howmuch.domain.entity.MyEvent;
import com.example.howmuch.domain.entity.MyEventDetail;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateMyEventDetailRequestDto {

    private String acquaintanceNickname;

    private long receiveAmount;

    public MyEventDetail toEntity(MyEvent myEvent) {
        return MyEventDetail.builder()
                .acquaintanceName(acquaintanceNickname)
                .myEvent(myEvent)
                .build();
    }
}
