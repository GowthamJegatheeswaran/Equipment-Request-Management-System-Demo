package com.uoj.equipment.dto;

import java.util.List;

public class AdminDepartmentUsersDTO {

    private String department;

    private List<SimpleUserDTO> hods;
    private List<SimpleUserDTO> tos;
    private List<SimpleUserDTO> lecturers;
    private List<SimpleUserDTO> staff;
    private List<SimpleUserDTO> students;

    public AdminDepartmentUsersDTO() {}

    public AdminDepartmentUsersDTO(String department,
                                   List<SimpleUserDTO> hods,
                                   List<SimpleUserDTO> tos,
                                   List<SimpleUserDTO> lecturers,
                                   List<SimpleUserDTO> staff,
                                   List<SimpleUserDTO> students) {
        this.department = department;
        this.hods = hods;
        this.tos = tos;
        this.lecturers = lecturers;
        this.staff = staff;
        this.students = students;
    }

    // getters/setters

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<SimpleUserDTO> getHods() {
        return hods;
    }

    public void setHods(List<SimpleUserDTO> hods) {
        this.hods = hods;
    }

    public List<SimpleUserDTO> getTos() {
        return tos;
    }

    public void setTos(List<SimpleUserDTO> tos) {
        this.tos = tos;
    }

    public List<SimpleUserDTO> getLecturers() {
        return lecturers;
    }

    public void setLecturers(List<SimpleUserDTO> lecturers) {
        this.lecturers = lecturers;
    }

    public List<SimpleUserDTO> getStaff() {
        return staff;
    }

    public void setStaff(List<SimpleUserDTO> staff) {
        this.staff = staff;
    }

    public List<SimpleUserDTO> getStudents() {
        return students;
    }

    public void setStudents(List<SimpleUserDTO> students) {
        this.students = students;
    }
}
