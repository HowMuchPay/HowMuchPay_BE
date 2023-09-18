package com.example.howmuch.dto.event;

import com.example.howmuch.domain.entity.MyEvent;
import com.example.howmuch.domain.entity.MyEventDetail;
import com.example.howmuch.domain.entity.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateMyEventDetailRequestDto {

    @NotBlank
    @Size(max = 255)
    private String acquaintanceNickname;

    @NotNull
    private long receiveAmount;

    public MyEventDetail toEntity(MyEvent myEvent, User user) {
        return MyEventDetail.builder()
            .user(user)
            .acquaintanceNickname(acquaintanceNickname)
            .receiveAmount(receiveAmount)
            .myEvent(myEvent)
            .build();
    }
}
