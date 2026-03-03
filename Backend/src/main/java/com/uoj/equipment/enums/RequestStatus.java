package com.uoj.equipment.enums;

public enum RequestStatus {

    // Student / Staff side
    PENDING_LECTURER_APPROVAL,

    // Lecturer side
    APPROVED_BY_LECTURER,
    REJECTED_BY_LECTURER,

    // TO side
    TO_PROCESSING,
    ISSUED_PENDING_STUDENT_ACCEPT,
    ISSUED_CONFIRMED,

    // Return flow
    RETURNED_PENDING_TO_VERIFY,
    RETURNED_VERIFIED,
    DAMAGED_REPORTED
}
