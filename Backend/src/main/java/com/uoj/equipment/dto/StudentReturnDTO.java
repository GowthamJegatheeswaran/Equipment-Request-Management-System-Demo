package com.uoj.equipment.dto;

import java.time.LocalDate;

public record StudentReturnDTO(
        Long requestId,
        String status,
        String message,
        String labName,
        String labDepartment,
        LocalDate fromDate,
        LocalDate toDate
) {}
