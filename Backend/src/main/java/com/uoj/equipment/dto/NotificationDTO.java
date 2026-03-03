package com.uoj.equipment.dto;

import com.uoj.equipment.enums.NotificationType;

import java.time.LocalDateTime;

public record NotificationDTO(
        Long id,
        NotificationType type,
        String title,
        String message,
        Long relatedRequestId,
        Long relatedPurchaseId,
        LocalDateTime createdAt,
        boolean readFlag
) {}
