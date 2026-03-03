package com.uoj.equipment.dto;

import java.time.LocalDate;

public record StudentAcceptanceDTO(
        Long requestId,
        String status,
        String message,
        String labName,
        String labDepartment,
        String lecturerName,
        LocalDate fromDate,
        LocalDate toDate
) {}
