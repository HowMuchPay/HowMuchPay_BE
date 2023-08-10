package com.example.howmuch.test;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class GlobalTest {

    @Test
    public void localDateTest() {

        LocalDate eventAt = LocalDate.of(2023, 8, 20);
        LocalDate now = LocalDate.now(); // 2023/08/11

        long diff = ChronoUnit.DAYS.between(eventAt, now);
        System.out.println(diff);
    }
}
