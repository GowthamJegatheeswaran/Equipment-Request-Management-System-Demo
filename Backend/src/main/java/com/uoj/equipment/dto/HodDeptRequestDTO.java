package com.uoj.equipment.dto;

import java.time.LocalDate;
import java.util.List;

public class HodDeptRequestDTO {
    private Long requestId;
    private Long labId;
    private String labName;

    private Long requesterId;
    private String requesterName;
    private String requesterRegNo;

    private String status;
    private String purpose;
    private LocalDate fromDate;
    private LocalDate toDate;

    private List<HodDeptRequestItemDTO> items;

    public HodDeptRequestDTO() {}

    public HodDeptRequestDTO(Long requestId, Long labId, String labName,
                             Long requesterId, String requesterName, String requesterRegNo,
                             String status, String purpose, LocalDate fromDate, LocalDate toDate,
                             List<HodDeptRequestItemDTO> items) {
        this.requestId = requestId;
        this.labId = labId;
        this.labName = labName;
        this.requesterId = requesterId;
        this.requesterName = requesterName;
        this.requesterRegNo = requesterRegNo;
        this.status = status;
        this.purpose = purpose;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.items = items;
    }

    public Long getRequestId() { return requestId; }
    public Long getLabId() { return labId; }
    public String getLabName() { return labName; }
    public Long getRequesterId() { return requesterId; }
    public String getRequesterName() { return requesterName; }
    public String getRequesterRegNo() { return requesterRegNo; }
    public String getStatus() { return status; }
    public String getPurpose() { return purpose; }
    public LocalDate getFromDate() { return fromDate; }
    public LocalDate getToDate() { return toDate; }
    public List<HodDeptRequestItemDTO> getItems() { return items; }

    public void setRequestId(Long requestId) { this.requestId = requestId; }
    public void setLabId(Long labId) { this.labId = labId; }
    public void setLabName(String labName) { this.labName = labName; }
    public void setRequesterId(Long requesterId) { this.requesterId = requesterId; }
    public void setRequesterName(String requesterName) { this.requesterName = requesterName; }
    public void setRequesterRegNo(String requesterRegNo) { this.requesterRegNo = requesterRegNo; }
    public void setStatus(String status) { this.status = status; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }
    public void setItems(List<HodDeptRequestItemDTO> items) { this.items = items; }
}
