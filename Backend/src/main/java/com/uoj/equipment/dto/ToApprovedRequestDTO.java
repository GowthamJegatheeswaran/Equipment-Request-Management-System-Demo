package com.uoj.equipment.dto;

import com.uoj.equipment.enums.PurposeType;

import java.time.LocalDate;
import java.util.List;

public record ToApprovedRequestDTO(
        Long requestId,
        String status,
        Integer priorityScore,
        PurposeType purpose,
        LocalDate fromDate,
        LocalDate toDate,

        Long requesterId,
        String requesterEmail,
        String requesterFullName,
        String requesterRegNo,
        String requesterDepartment,

        Long labId,
        String labName,
        String labDepartment,

        Long lecturerId,
        String lecturerFullName,
        String lecturerDepartment,

        List<ToApprovedRequestItemDTO> items
) {}
