package com.uoj.equipment.service;

import com.uoj.equipment.enums.PurposeType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class PriorityService {

    public int calculate(PurposeType purpose,
                         LocalDate fromDate,
                         LocalDate toDate,
                         boolean specialFlag) {

        int base = switch (purpose) {
            case RESEARCH -> 30;
            case PROJECT -> 40;
            case LABS -> 70;
            case LECTURE -> 55;
            case PERSONAL -> 15;
        };

        // Short duration = priority +10
        long days = ChronoUnit.DAYS.between(fromDate, toDate) + 1;
        if (days <= 1) base += 10;

        if (specialFlag) base += 20;

        return base;
    }
}
