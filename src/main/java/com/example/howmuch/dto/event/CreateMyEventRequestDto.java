package com.example.howmuch.dto.event;

import com.example.howmuch.contant.EventCategory;
import com.example.howmuch.contant.MyType;
import com.example.howmuch.domain.entity.MyEvent;
import com.example.howmuch.domain.entity.User;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateMyEventRequestDto {

    @NotNull
    private LocalDate eventAt;

    @NotNull
    private Integer myType;

    @NotNull
    private Integer eventCategory;

    public MyEvent toEntity(User user) {
        return MyEvent.builder()
                .eventAt(eventAt)
                .totalReceiveAmount(0L)
                .myType(MyType.fromValue(myType))
                .eventCategory(EventCategory.fromValue(eventCategory))
                .user(user)
                .build();
    }
}
