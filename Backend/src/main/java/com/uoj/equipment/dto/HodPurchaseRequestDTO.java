package com.uoj.equipment.dto;

import com.uoj.equipment.enums.PurchaseStatus;

import java.time.LocalDate;
import java.util.List;

public record HodPurchaseRequestDTO(
        Long id,
        String department,
        String toName,              // TO who created this purchase
        LocalDate createdDate,
        PurchaseStatus status,
        String reason,
        List<HodPurchaseItemDTO> items
) {}
