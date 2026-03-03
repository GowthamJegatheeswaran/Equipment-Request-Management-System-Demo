package com.uoj.equipment.entity;

import com.uoj.equipment.enums.RequestItemStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "request_items")
public class RequestItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private EquipmentRequest request;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "issued_qty", nullable = false)
    private int issuedQty = 0;

    @Column(nullable = false)
    private boolean returned = false;

    @Column(nullable = false)
    private boolean damaged = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 64)
    private RequestItemStatus status = RequestItemStatus.PENDING_LECTURER_APPROVAL;

    // Optional reason when TO sets the item as waiting (not issued yet)
    @Column(name = "to_wait_reason", length = 255)
    private String toWaitReason;

    public RequestItem() {}

    public Long getId() { return id; }
    public EquipmentRequest getRequest() { return request; }
    public Equipment getEquipment() { return equipment; }
    public int getQuantity() { return quantity; }
    public int getIssuedQty() { return issuedQty; }
    public boolean isReturned() { return returned; }
    public boolean isDamaged() { return damaged; }
    public RequestItemStatus getStatus() { return status; }
    public String getToWaitReason() { return toWaitReason; }

    public void setId(Long id) { this.id = id; }
    public void setRequest(EquipmentRequest request) { this.request = request; }
    public void setEquipment(Equipment equipment) { this.equipment = equipment; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setIssuedQty(int issuedQty) { this.issuedQty = issuedQty; }
    public void setReturned(boolean returned) { this.returned = returned; }
    public void setDamaged(boolean damaged) { this.damaged = damaged; }
    public void setStatus(RequestItemStatus status) { this.status = status; }
    public void setToWaitReason(String toWaitReason) { this.toWaitReason = toWaitReason; }
}
