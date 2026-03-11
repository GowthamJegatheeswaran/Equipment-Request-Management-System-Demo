package com.uoj.equipment.entity;

import com.uoj.equipment.enums.PurposeType;
import com.uoj.equipment.enums.RequestStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "equipment_requests")
public class EquipmentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    private User requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecturer_id")
    private User lecturer;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "lab_id")
    private Lab lab;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private PurposeType purpose;

    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

    // ── NEW: time fields (nullable — backward compatible with old rows) ──
    @Column(name = "from_time")
    private LocalTime fromTime;

    @Column(name = "to_time")
    private LocalTime toTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 60)
    private RequestStatus status = RequestStatus.PENDING_LECTURER_APPROVAL;

    @Column(name = "priority_score", nullable = false)
    private int priorityScore = 0;

    @Column(name = "letter_attachment_path", length = 400)
    private String letterAttachmentPath;

    public EquipmentRequest() {}

    public Long getId()                          { return id; }
    public User getRequester()                   { return requester; }
    public User getLecturer()                    { return lecturer; }
    public Lab getLab()                          { return lab; }
    public PurposeType getPurpose()              { return purpose; }
    public LocalDate getFromDate()               { return fromDate; }
    public LocalDate getToDate()                 { return toDate; }
    public LocalTime getFromTime()               { return fromTime; }   // NEW
    public LocalTime getToTime()                 { return toTime; }     // NEW
    public RequestStatus getStatus()             { return status; }
    public int getPriorityScore()                { return priorityScore; }
    public String getLetterAttachmentPath()      { return letterAttachmentPath; }

    public void setId(Long id)                                     { this.id = id; }
    public void setRequester(User requester)                       { this.requester = requester; }
    public void setLecturer(User lecturer)                         { this.lecturer = lecturer; }
    public void setLab(Lab lab)                                    { this.lab = lab; }
    public void setPurpose(PurposeType purpose)                    { this.purpose = purpose; }
    public void setFromDate(LocalDate fromDate)                    { this.fromDate = fromDate; }
    public void setToDate(LocalDate toDate)                        { this.toDate = toDate; }
    public void setFromTime(LocalTime fromTime)                    { this.fromTime = fromTime; }  // NEW
    public void setToTime(LocalTime toTime)                        { this.toTime = toTime; }      // NEW
    public void setStatus(RequestStatus status)                    { this.status = status; }
    public void setPriorityScore(int priorityScore)                { this.priorityScore = priorityScore; }
    public void setLetterAttachmentPath(String letterAttachmentPath) { this.letterAttachmentPath = letterAttachmentPath; }
}