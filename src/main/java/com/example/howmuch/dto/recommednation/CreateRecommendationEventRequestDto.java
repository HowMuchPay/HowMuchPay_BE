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
    private Integer eventCategory;
    @NotNull
    private Integer acquaintanceType;
    @NotNull
    private long payAmount;
    @NotNull
    private List<Boolean> intimacyAnswers;
    @NotNull
    private String ageGroup;
    @NotNull
    private int annualIncome;

}