package com.uoj.equipment.dto;

import com.uoj.equipment.enums.PurposeType;
import java.time.LocalDate;
import java.util.List;

public class NewRequestDTO {

    private Long labId;
    private Long lecturerId;

    private PurposeType purpose;      // DROPDOWN
    private String purposeNote;       // OPTIONAL TEXT

    private LocalDate fromDate;
    private LocalDate toDate;

    private String letterAttachmentPath;

    private List<ItemLine> items;

    public static class ItemLine {
        private Long equipmentId;
        private int quantity;

        // getters & setters

        public Long getEquipmentId() {
            return equipmentId;
        }

        public void setEquipmentId(Long equipmentId) {
            this.equipmentId = equipmentId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    // getters & setters


    public Long getLabId() {
        return labId;
    }

    public void setLabId(Long labId) {
        this.labId = labId;
    }

    public Long getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Long lecturerId) {
        this.lecturerId = lecturerId;
    }

    public PurposeType getPurpose() {
        return purpose;
    }

    public void setPurpose(PurposeType purpose) {
        this.purpose = purpose;
    }

    public String getPurposeNote() {
        return purposeNote;
    }

    public void setPurposeNote(String purposeNote) {
        this.purposeNote = purposeNote;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getLetterAttachmentPath() {
        return letterAttachmentPath;
    }

    public void setLetterAttachmentPath(String letterAttachmentPath) {
        this.letterAttachmentPath = letterAttachmentPath;
    }

    public List<ItemLine> getItems() {
        return items;
    }

    public void setItems(List<ItemLine> items) {
        this.items = items;
    }
}
