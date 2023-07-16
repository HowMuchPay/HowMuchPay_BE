package com.example.howmuch.dto.recommednation;

import com.example.howmuch.contant.AcType;
import com.example.howmuch.contant.EventCategory;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CreateRecommendationEventRequestDto {

    @NotNull
    private EventCategory eventCategory;
    @NotNull
    private AcType acquaintanceType;
    @NotNull
    private long payAmount;
    @NotNull
    private List<Boolean> intimacyAnswers;

}