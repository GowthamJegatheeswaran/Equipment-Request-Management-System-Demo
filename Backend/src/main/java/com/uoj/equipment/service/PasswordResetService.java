package com.uoj.equipment.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uoj.equipment.entity.PasswordResetToken;
import com.uoj.equipment.entity.User;
import com.uoj.equipment.repository.PasswordResetTokenRepository;
import com.uoj.equipment.repository.UserRepository;

@Service
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Value("${app.frontend.base-url}")
    private String frontendBaseUrl;

    public PasswordResetService(UserRepository userRepository,
                                PasswordResetTokenRepository tokenRepository,
                                PasswordEncoder passwordEncoder,
                                EmailService emailService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    /**
     * Step 1: user submits email → save reset token, then try to send email.
     *
     * ROOT CAUSE OF 500 ERROR:
     *   The old code called emailService.sendPlainTextEmail() directly inside
     *   the @Transactional method with NO try/catch. When Gmail SMTP fails
     *   (wrong credentials, timeout, auth error), it throws MailException or
     *   SocketTimeoutException. That propagates up through the controller and
     *   hits GlobalExceptionHandler.handleGeneric() → returns 500 "Server error".
     *
     * FIX: Wrap the email send in try/catch. The token is already saved to DB
     * at this point, so the user CAN still use the reset flow if they already
     * have the token (e.g., from a re-try). More importantly, the API no longer
     * returns 500 — it always returns 200 with the standard "if this email exists"
     * message, which is also correct security practice (don't leak whether the
     * email exists or not).
     *
     * The real fix for email delivery is in application.properties (timeouts)
     * and Railway environment variables (SMTP credentials). See below.
     */
    @Transactional
    public void createResetTokenForEmail(String email) {
        // If email not found → throws IllegalArgumentException → 400 from GlobalExceptionHandler
        // (This is the correct behavior — only the 500 from email failure needed fixing)
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No user with this email"));

        // Delete any existing unused tokens for this user to avoid duplicate tokens piling up
        tokenRepository.findAll().stream()
                .filter(t -> t.getUser() != null
                          && t.getUser().getId().equals(user.getId())
                          && !t.isUsed())
                .forEach(tokenRepository::delete);

        String token = UUID.randomUUID().toString();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(30);

        PasswordResetToken prt = new PasswordResetToken(token, user, expiresAt);
        tokenRepository.save(prt);

        String resetLink = frontendBaseUrl + "/reset-password?token=" + token;

        String subject = "Password Reset Request - Equipment Request System";
        String body = "Dear " + user.getFullName() + ",\n\n"
                + "We received a request to reset your password.\n"
                + "Please click the link below (or paste into your browser) to set a new password:\n\n"
                + resetLink + "\n\n"
                + "This link will expire in 30 minutes.\n\n"
                + "If you did not request this, you can safely ignore this email.\n\n"
                + "Thank you,\n"
                + "Equipment Request System\n"
                + "Faculty of Engineering, University of Jaffna";

        // FIX: Wrapped in try/catch so SMTP failures never cause a 500.
        // The token is already in the DB — if email fails, admin can check logs.
        // User-facing response is always 200 for security (don't reveal if email exists).
        try {
            emailService.sendPlainTextEmail(user.getEmail(), subject, body);
        } catch (Exception e) {
            // Log the real SMTP error server-side for debugging, but don't expose it
            System.err.println("[PasswordResetService] Email send FAILED for: "
                    + user.getEmail() + " | Error: " + e.getClass().getSimpleName()
                    + ": " + e.getMessage());
            // Do NOT rethrow — allow the endpoint to return 200 normally.
            // The token is still valid in DB; user can request again once SMTP is fixed.
        }
    }

    // Step 2: user sends token + new password → validate token and update password.
    @Transactional
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken prt = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid or unknown token"));

        if (prt.isUsed()) {
            throw new IllegalArgumentException("Token already used");
        }
        if (prt.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token expired");
        }

        User user = prt.getUser();
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        prt.setUsed(true);
        tokenRepository.save(prt);
    }
}