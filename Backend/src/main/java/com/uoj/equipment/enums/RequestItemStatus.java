package com.uoj.equipment.enums;

/**
 * Per-equipment (per row in request_items) workflow status.
 */
public enum RequestItemStatus {
    // Lecturer approval flow
    PENDING_LECTURER_APPROVAL,
    APPROVED_BY_LECTURER,
    REJECTED_BY_LECTURER,

    // TO issue flow
    WAITING_TO_ISSUE,
    ISSUED_PENDING_REQUESTER_ACCEPT,
    ISSUED_CONFIRMED,

    // Return flow (only for RETURNABLE items)
    RETURN_REQUESTED,
    RETURN_VERIFIED,

    // Damage
    DAMAGED_REPORTED
}
