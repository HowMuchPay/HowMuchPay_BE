package com.example.howmuch.dto.recommednation;

import com.example.howmuch.contant.AcType;
import com.example.howmuch.contant.EventCategory;
import com.example.howmuch.domain.entity.MyEvent;
import com.example.howmuch.domain.entity.User;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RecommendationEventRequestDto {

    @NotNull
    private EventCategory eventCategory;
    @NotNull
    private AcType acquaintanceType;
    @NotNull
    private long payAmount;
    @NotNull
    private List<Boolean> intimacyAnswers;

}