package com.example.howmuch.dto.recommednation;

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
public class CalculateAverageAmountRequestDto {

    @NotNull
    private Integer eventCategory;

    @NotNull
    private Integer acquaintanceType;

    @NotNull
    private List<Boolean> intimacyAnswers;

    @Min(1)
    @Max(10)
    private int ageGroup;
    private int annualIncome;
}
