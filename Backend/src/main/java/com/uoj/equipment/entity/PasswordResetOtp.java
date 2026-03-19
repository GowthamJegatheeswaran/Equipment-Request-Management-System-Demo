package com.uoj.equipment.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_otps")
public class PasswordResetOtp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 6)
    private String otp;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "used", nullable = false)
    private boolean used = false;

    public PasswordResetOtp() {}

    public PasswordResetOtp(String otp, User user, LocalDateTime expiresAt) {
        this.otp       = otp;
        this.user      = user;
        this.expiresAt = expiresAt;
        this.used      = false;
    }

    public Long          getId()                       { return id; }
    public String        getOtp()                      { return otp; }
    public void          setOtp(String otp)            { this.otp = otp; }
    public User          getUser()                     { return user; }
    public void          setUser(User user)            { this.user = user; }
    public LocalDateTime getExpiresAt()                { return expiresAt; }
    public void          setExpiresAt(LocalDateTime e) { this.expiresAt = e; }
    public boolean       isUsed()                      { return used; }
    public void          setUsed(boolean used)         { this.used = used; }
}