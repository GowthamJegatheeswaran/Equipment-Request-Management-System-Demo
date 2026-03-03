package com.uoj.equipment.dto;

import java.time.LocalDate;
import java.util.List;

public record ToIssueResponseDTO(
        Long requestId,
        String status,
        String labName,
        String labDepartment,
        String requesterName,
        String requesterRegNo,
        String lecturerName,
        LocalDate fromDate,
        LocalDate toDate,
        List<IssuedItemDTO> items
) {}
