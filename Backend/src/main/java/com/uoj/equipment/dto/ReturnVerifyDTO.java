package com.uoj.equipment.dto;

public class ReturnVerifyDTO {

    private Long requestId;
    private boolean damaged;

    public ReturnVerifyDTO() {}

    public Long getRequestId() { return requestId; }
    public boolean isDamaged() { return damaged; }

    public void setRequestId(Long requestId) { this.requestId = requestId; }
    public void setDamaged(boolean damaged) { this.damaged = damaged; }
}
