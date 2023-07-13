package com.example.howmuch.dto.event;

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
    private Integer eventCategory;
}
