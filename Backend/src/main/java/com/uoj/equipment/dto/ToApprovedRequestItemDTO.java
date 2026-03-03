package com.uoj.equipment.dto;

public record ToApprovedRequestItemDTO(
        Long requestItemId,
        Long equipmentId,
        String equipmentName,
        String category,
        String itemType,
        Integer quantity,
        Integer availableQty,
        String itemStatus,
        String toWaitReason,
        Integer issuedQty,
        Boolean returned,
        Boolean damaged
) {}
