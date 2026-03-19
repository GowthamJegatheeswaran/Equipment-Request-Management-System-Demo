package com.uoj.equipment.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uoj.equipment.entity.PasswordResetOtp;
import com.uoj.equipment.entity.User;
import com.uoj.equipment.repository.PasswordResetOtpRepository;
import com.uoj.equipment.repository.UserRepository;

@Service
public class PasswordResetService {

    private final UserRepository             userRepository;
    private final PasswordResetOtpRepository otpRepository;
    private final PasswordEncoder            passwordEncoder;
    private final EmailService               emailService;

    private static final SecureRandom RANDOM = new SecureRandom();

    public PasswordResetService(UserRepository userRepository,
                                PasswordResetOtpRepository otpRepository,
                                PasswordEncoder passwordEncoder,
                                EmailService emailService) {
        this.userRepository  = userRepository;
        this.otpRepository   = otpRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService    = emailService;
    }

    // ── Step 1: generate OTP and send it by email ────────────────────────────
    @Transactional
    public void sendOtpForEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No account found with this email address."));

        // Delete all previous OTPs for this user (one active OTP at a time)
        otpRepository.deleteAllByUserEmail(email);

        // Generate a random 6-digit OTP, zero-padded  e.g. "048392"
        String otp = String.format("%06d", RANDOM.nextInt(1_000_000));

        // OTP valid for 10 minutes
        otpRepository.save(new PasswordResetOtp(otp, user,
                LocalDateTime.now().plusMinutes(10)));

        String subject = "[ERMS] Your Password Reset OTP";
        String body =
                "Dear " + user.getFullName() + ",\n\n"
              + "You requested a password reset for your ERMS account.\n\n"
              + "Your One-Time Password (OTP) is:\n\n"
              + "        " + otp + "\n\n"
              + "This OTP expires in 10 minutes.\n"
              + "Do NOT share this code with anyone.\n\n"
              + "If you did not request this, you can safely ignore this email.\n\n"
              + "Equipment Request System\n"
              + "Faculty of Engineering, University of Jaffna";

        emailService.sendPlainTextEmail(user.getEmail(), subject, body);
    }

    // ── Step 2: verify OTP + set new password ────────────────────────────────
    @Transactional
    public void resetPasswordWithOtp(String email, String otp, String newPassword) {
        PasswordResetOtp record = otpRepository
                .findTopByUser_EmailAndOtpAndUsedFalseOrderByIdDesc(email, otp)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid OTP. Please check the code and try again."));

        if (record.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException(
                    "OTP has expired. Please request a new one.");

        User user = record.getUser();
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        record.setUsed(true);
        otpRepository.save(record);
    }
}