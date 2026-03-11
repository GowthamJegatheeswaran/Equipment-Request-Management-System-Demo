package com.uoj.equipment.dto;

import com.uoj.equipment.enums.PurposeType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class NewRequestDTO {

    private Long labId;
    private Long lecturerId;

    private PurposeType purpose;
    private String purposeNote;

    private LocalDate fromDate;
    private LocalDate toDate;

    // ── NEW: optional time fields ──
    // Frontend sends "HH:mm" strings; Jackson deserialises to LocalTime automatically.
    // Nullable — old clients that send no time still work fine.
    private LocalTime fromTime;
    private LocalTime toTime;

    private String letterAttachmentPath;
    private List<ItemLine> items;

    public static class ItemLine {
        private Long equipmentId;
        private int quantity;

        public Long getEquipmentId()              { return equipmentId; }
        public void setEquipmentId(Long id)       { this.equipmentId = id; }
        public int getQuantity()                  { return quantity; }
        public void setQuantity(int q)            { this.quantity = q; }
    }

    public Long getLabId()                        { return labId; }
    public void setLabId(Long labId)              { this.labId = labId; }

    public Long getLecturerId()                   { return lecturerId; }
    public void setLecturerId(Long id)            { this.lecturerId = id; }

    public PurposeType getPurpose()               { return purpose; }
    public void setPurpose(PurposeType p)         { this.purpose = p; }

    public String getPurposeNote()                { return purposeNote; }
    public void setPurposeNote(String n)          { this.purposeNote = n; }

    public LocalDate getFromDate()                { return fromDate; }
    public void setFromDate(LocalDate d)          { this.fromDate = d; }

    public LocalDate getToDate()                  { return toDate; }
    public void setToDate(LocalDate d)            { this.toDate = d; }

    public LocalTime getFromTime()                { return fromTime; }   // NEW
    public void setFromTime(LocalTime t)          { this.fromTime = t; } // NEW

    public LocalTime getToTime()                  { return toTime; }     // NEW
    public void setToTime(LocalTime t)            { this.toTime = t; }   // NEW

    public String getLetterAttachmentPath()       { return letterAttachmentPath; }
    public void setLetterAttachmentPath(String p) { this.letterAttachmentPath = p; }

    public List<ItemLine> getItems()              { return items; }
    public void setItems(List<ItemLine> items)    { this.items = items; }
}