package com.uoj.equipment.repository;

import com.uoj.equipment.entity.PasswordResetOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PasswordResetOtpRepository extends JpaRepository<PasswordResetOtp, Long> {

    Optional<PasswordResetOtp> findTopByUser_EmailAndOtpAndUsedFalseOrderByIdDesc(
            String email, String otp);

    @Modifying
    @Transactional
    @Query("DELETE FROM PasswordResetOtp o WHERE o.user.email = :email")
    void deleteAllByUserEmail(@Param("email") String email);
}