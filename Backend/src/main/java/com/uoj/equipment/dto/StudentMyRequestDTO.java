package com.uoj.equipment.dto;

import com.uoj.equipment.enums.PurposeType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record StudentMyRequestDTO(
        Long requestId,
        String status,
        PurposeType purpose,
        LocalDate fromDate,
        LocalDate toDate,
        LocalTime fromTime,          // NEW — nullable
        LocalTime toTime,            // NEW — nullable
        String labName,
        String lecturerName,
        List<StudentMyRequestItemDTO> items,
        boolean canAcceptIssue,
        boolean canReturn
) {}