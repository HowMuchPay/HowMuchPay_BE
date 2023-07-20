package com.example.howmuch.dto.recommednation;

import com.example.howmuch.contant.AcType;
import com.example.howmuch.contant.EventCategory;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CreateRecommendationEventRequestDto {

    @Min(0)
    @Max(3)
    private Integer eventCategory;
    @Min(0)
    @Max(3)
    private Integer acquaintanceType;
    @NotNull
    private long payAmount;
    @NotNull
    private List<Boolean> intimacyAnswers;
    @Min(1)
    @Max(10)
    private int ageGroup;
    private int annualIncome;
}