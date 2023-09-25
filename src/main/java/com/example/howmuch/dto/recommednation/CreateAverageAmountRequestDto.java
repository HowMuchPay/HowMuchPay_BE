package com.example.howmuch.dto.recommednation;

import lombok.*;


import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAverageAmountRequestDto {
    //나이 20~ 70대
    @NotNull
    private Integer ageGroup;

    private String phoneNumber;

    //연수입 3000~ 8000
    @NotNull
    private Integer annualIncome;

    @NotNull
    private List<RelationInfo> relationInfoList = new ArrayList<>();
}
