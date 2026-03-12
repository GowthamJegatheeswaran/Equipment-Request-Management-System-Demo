package com.uoj.equipment.dto;

public class ChangePasswordDTO {

    private String currentPassword;
    private String newPassword;

    // Default constructor (required for JSON binding)
    public ChangePasswordDTO() {}

    // Getters and Setters
    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}