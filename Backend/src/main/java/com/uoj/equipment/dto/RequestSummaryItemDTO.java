package com.uoj.equipment.dto;

public record RequestSummaryItemDTO(
        Long requestItemId,
        Long equipmentId,
        String equipmentName,
        String category,
        String itemType,
        Integer quantity,
        String itemStatus,
        String toWaitReason,
        Integer issuedQty,
        Boolean returned,
        Boolean damaged
) {}
