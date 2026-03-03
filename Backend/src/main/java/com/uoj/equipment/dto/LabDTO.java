package com.uoj.equipment.dto;

public class LabDTO {
    private Long id;
    private String name;
    private String department;
    private Long technicalOfficerId;

    public LabDTO() {}

    public LabDTO(Long id, String name, String department, Long technicalOfficerId) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.technicalOfficerId = technicalOfficerId;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public Long getTechnicalOfficerId() { return technicalOfficerId; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }
    public void setTechnicalOfficerId(Long technicalOfficerId) { this.technicalOfficerId = technicalOfficerId; }
}
