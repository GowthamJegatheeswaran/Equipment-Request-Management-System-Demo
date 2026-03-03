package com.uoj.equipment.dto;

public class HodDeptRequestItemDTO {
    private Long requestItemId;
    private Long equipmentId;
    private String equipmentName;
    private int quantity;
    private int issuedQty;
    private boolean returned;
    private boolean damaged;
    private String status;

    public HodDeptRequestItemDTO() {}

    public HodDeptRequestItemDTO(Long requestItemId, Long equipmentId, String equipmentName,
                                 int quantity, int issuedQty, boolean returned, boolean damaged, String status) {
        this.requestItemId = requestItemId;
        this.equipmentId = equipmentId;
        this.equipmentName = equipmentName;
        this.quantity = quantity;
        this.issuedQty = issuedQty;
        this.returned = returned;
        this.damaged = damaged;
        this.status = status;
    }

    public Long getRequestItemId() { return requestItemId; }
    public Long getEquipmentId() { return equipmentId; }
    public String getEquipmentName() { return equipmentName; }
    public int getQuantity() { return quantity; }
    public int getIssuedQty() { return issuedQty; }
    public boolean isReturned() { return returned; }
    public boolean isDamaged() { return damaged; }
    public String getStatus() { return status; }

    public void setRequestItemId(Long requestItemId) { this.requestItemId = requestItemId; }
    public void setEquipmentId(Long equipmentId) { this.equipmentId = equipmentId; }
    public void setEquipmentName(String equipmentName) { this.equipmentName = equipmentName; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setIssuedQty(int issuedQty) { this.issuedQty = issuedQty; }
    public void setReturned(boolean returned) { this.returned = returned; }
    public void setDamaged(boolean damaged) { this.damaged = damaged; }
    public void setStatus(String status) { this.status = status; }
}
