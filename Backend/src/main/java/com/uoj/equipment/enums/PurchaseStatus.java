package com.uoj.equipment.enums;

public enum PurchaseStatus {

    SUBMITTED_TO_HOD,
    REJECTED_BY_HOD,
    APPROVED_BY_HOD,

    REJECTED_BY_ADMIN,

    /**
     * Admin has issued/approved the purchase ("Given Date" is set).
     * This is the new workflow step.
     */
    ISSUED_BY_ADMIN,

    /**
     * Backward-compatibility for older rows (can be migrated to ISSUED_BY_ADMIN).
     */
    APPROVED_BY_ADMIN,

    /**
     * HOD confirmed items received (inventory updated).
     */
    RECEIVED_BY_HOD,

    /**
     * Legacy status from previous workflow.
     */
    RECEIVED_BY_TO
}
