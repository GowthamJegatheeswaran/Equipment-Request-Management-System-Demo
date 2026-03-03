package com.uoj.equipment.dto;

import com.uoj.equipment.enums.PurposeType;

import java.time.LocalDate;
import java.util.List;

public record RequestSummaryDTO(
        Long requestId,
        String status,
        PurposeType purpose,
        LocalDate fromDate,
        LocalDate toDate,
        String labName,
        String labDepartment,
        String lecturerFullName,
        String requesterFullName,
        String requesterRegNo,
        String requesterRole,

        List<RequestSummaryItemDTO> items
) {}
