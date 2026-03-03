package com.uoj.equipment.entity;

import com.uoj.equipment.enums.PurchaseStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchase_requests")
public class PurchaseRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // EEE / COM / etc.
    private String department;

    // TO who created this purchase request
    @ManyToOne
    @JoinColumn(name = "to_id")
    private User toUser;

    // HOD who will approve/reject
    @ManyToOne
    @JoinColumn(name = "hod_id")
    private User hodUser;

    private LocalDate createdDate;

    // When Admin issues/approves the purchase ("Given Date")
    @Column(name = "issued_date")
    private LocalDate issuedDate;

    // When HOD confirms received items after admin issue
    @Column(name = "received_date")
    private LocalDate receivedDate;

    @Enumerated(EnumType.STRING)
    private PurchaseStatus status;

    @Column(length = 1000)
    private String reason;

    @OneToMany(mappedBy = "purchaseRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseItem> items = new ArrayList<>();

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }

    public User getToUser() {
        return toUser;
    }
    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public User getHodUser() {
        return hodUser;
    }
    public void setHodUser(User hodUser) {
        this.hodUser = hodUser;
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

    public List<PurchaseItem> getItems() {
        return items;
    }
    public void setItems(List<PurchaseItem> items) {
        this.items = items;
    }
}
