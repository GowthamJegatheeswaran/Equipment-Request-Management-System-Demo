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
    // Split into two methods: DB work inside @Transactional, email send OUTSIDE
    // so that a failed email does not roll back the saved OTP, and the real
    // exception is not swallowed by the transaction manager.
    public void sendOtpForEmail(String email) {
        // 1. Save OTP in a separate committed transaction first
        String[] result = saveOtp(email);
        String userEmail   = result[0];
        String fullName    = result[1];
        String otp         = result[2];

        // 2. Send email AFTER the transaction has committed — errors propagate cleanly
        String subject = "[ERMS] Your Password Reset OTP";
        String body =
                "Dear " + fullName + ",\n\n"
              + "You requested a password reset for your ERMS account.\n\n"
              + "Your One-Time Password (OTP) is:\n\n"
              + "        " + otp + "\n\n"
              + "This OTP expires in 10 minutes.\n"
              + "Do NOT share this code with anyone.\n\n"
              + "If you did not request this, you can safely ignore this email.\n\n"
              + "Equipment Request System\n"
              + "Faculty of Engineering, University of Jaffna";

        emailService.sendPlainTextEmail(userEmail, subject, body);
    }

    @Transactional
    protected String[] saveOtp(String email) {
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

        return new String[]{ user.getEmail(), user.getFullName(), otp };
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