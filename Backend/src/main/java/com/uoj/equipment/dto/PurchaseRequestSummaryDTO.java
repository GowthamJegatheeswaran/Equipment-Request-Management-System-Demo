package com.uoj.equipment.dto;

import com.uoj.equipment.enums.PurchaseStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PurchaseRequestSummaryDTO {
    private String message;
    private Long id;
    private String department;
    private PurchaseStatus status;
    private String reason;
    private LocalDate createdDate;
    private LocalDate issuedDate;
    private LocalDate receivedDate;

    private String requestedByName;
    private String requestedByEmail;

    private List<ItemLine> items;



    public static class ItemLine {
        private String equipmentName;
        private int quantity;

        public ItemLine(String equipmentName, int quantity) {
            this.equipmentName = equipmentName;
            this.quantity = quantity;
        }

        // getters/setters...

        public String getEquipmentName() {
            return equipmentName;
        }

        public void setEquipmentName(String equipmentName) {
            this.equipmentName = equipmentName;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    // constructors + getters/setters


    public PurchaseRequestSummaryDTO(String message,Long id, String department, PurchaseStatus status, String reason, LocalDate createdDate, String requestedByName, String requestedByEmail, List<ItemLine> items) {
        this.message=message;
        this.id = id;
        this.department = department;
        this.status = status;
        this.reason = reason;
        this.createdDate = createdDate;
        this.issuedDate = null;
        this.receivedDate = null;
        this.requestedByName = requestedByName;
        this.requestedByEmail = requestedByEmail;
        this.items = items;
    }


    public PurchaseRequestSummaryDTO() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public PurchaseStatus getStatus() {
        return status;
    }

    public void setStatus(PurchaseStatus status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    
public LocalDate getIssuedDate() {
    return issuedDate;
}

public void setIssuedDate(LocalDate issuedDate) {
    this.issuedDate = issuedDate;
}

public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getRequestedByName() {
        return requestedByName;
    }

    public void setRequestedByName(String requestedByName) {
        this.requestedByName = requestedByName;
    }

    public String getRequestedByEmail() {
        return requestedByEmail;
    }

    public void setRequestedByEmail(String requestedByEmail) {
        this.requestedByEmail = requestedByEmail;
    }

    public List<ItemLine> getItems() {
        return items;
    }

    public void setItems(List<ItemLine> items) {
        this.items = items;
    }
}
