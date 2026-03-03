package com.uoj.equipment.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "labs")
public class Lab {



    @ManyToOne
    @JoinColumn(name = "to_id")
    private User technicalOfficer;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 20)
    private String department;

    public Lab() {}

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }
    public User getTechnicalOfficer() {
        return technicalOfficer;
    }

    public void setTechnicalOfficer(User technicalOfficer) {
        this.technicalOfficer = technicalOfficer;
    }
}
