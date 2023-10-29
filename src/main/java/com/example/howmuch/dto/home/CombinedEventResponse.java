package com.example.howmuch.dto.home;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class CombinedEventResponse {
    private Long id;
    private String displayName;
    private LocalDate eventAt;
    private Long amount;
    private int category;
    private String eventType;

    public CombinedEventResponse(Long id, String displayName, LocalDate eventAt, Long amount, int category, String eventType) {
        this.id = id;
        this.displayName = displayName;
        this.eventAt = eventAt;
        this.amount = amount;
        this.category = category;
        this.eventType = eventType;
    }
}