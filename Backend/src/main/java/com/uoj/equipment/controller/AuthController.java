package com.uoj.equipment.controller;
import com.uoj.equipment.dto.*;
import com.uoj.equipment.entity.User;
import com.uoj.equipment.enums.Role;
import com.uoj.equipment.repository.UserRepository;
import com.uoj.equipment.config.JwtUtil;
import com.uoj.equipment.service.PasswordResetService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordResetService passwordResetService;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    // Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        try {
            // 1) Authenticate credentials
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
            authenticationManager.authenticate(authToken);

            // 2) Load user from DB
            User user = userRepository.findByEmail(dto.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

            // 3) Generate JWT token
            String token = jwtUtil.generateToken(user.getEmail());

            // 4) Build response
            LoginResponseDTO resp = new LoginResponseDTO(
                    "Login successful",
                    token,
                    user.getEmail(),
                    user.getRole().name()

            );

            return ResponseEntity.ok(resp);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

    // Logged-in user profile (frontend uses this to filter labs/equipment by department)
    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication auth) {
        User user = userRepository.findByEmail(auth.getName()).orElse(null);
        if (user == null) return ResponseEntity.status(404).body("User not found");

        return ResponseEntity.ok(new SimpleUserDTO(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRegNo(),
                user.getDepartment(),
                user.getRole().name()
        ));
    }


    // Signup
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDTO dto) {


        if (dto.getFullName() == null || dto.getFullName().isBlank())
            return ResponseEntity.badRequest().body("Full name is required");

        if (dto.getEmail() == null || dto.getEmail().isBlank())
            return ResponseEntity.badRequest().body("Email is required");

        if (dto.getRegNo() == null || dto.getRegNo().isBlank())
            return ResponseEntity.badRequest().body("Register number is required");

        if (dto.getDepartment() == null || dto.getDepartment().isBlank())
            return ResponseEntity.badRequest().body("Department is required");

        if (dto.getPassword() == null || dto.getPassword().isBlank())
            return ResponseEntity.badRequest().body("Password is required");


        // Unique constraints
        if (userRepository.existsByEmail(dto.getEmail()))
            return ResponseEntity.badRequest().body("Email already exists");

        if (userRepository.existsByRegNo(dto.getRegNo()))
            return ResponseEntity.badRequest().body("Register number already exists");


        //Reg no format check -> 2022/E/063
        String regNoRegex = "^20[0-9]{2}/E/[0-9]{3}$";
        if (!dto.getRegNo().matches(regNoRegex)) {
            return ResponseEntity.badRequest().body("Invalid register number. Use format: 2022/E/063");
        }

        // Email format check -> 2022e063@eng.jfn.ac.lk
        String emailRegex = "^20[0-9]{2}e[0-9]{3}@eng\\.jfn\\.ac\\.lk$";
        if (!dto.getEmail().matches(emailRegex)) {
            return ResponseEntity.badRequest().body("Invalid student email. Use format: 2022e063@eng.jfn.ac.lk");
        }

        // Email match reg no (2022/E/063 -> 2022e063)
        String regNoClean = dto.getRegNo().replace("/", ""); // 2022E063
        // Build expected email prefix: 2022e063
        String expectedEmailPrefix =
                regNoClean.substring(0, 4) + "e" + regNoClean.substring(5);

        // Actual email prefix (before @)
        String actualEmailPrefix = dto.getEmail().split("@")[0];

        if (!expectedEmailPrefix.equalsIgnoreCase(actualEmailPrefix)) {
            return ResponseEntity.badRequest().body(
                    "Email does not match register number. Expected email prefix: " + expectedEmailPrefix
                            + "@eng.jfn.ac.lk"
            );
        }

        // FORCE ROLE = STUDENT (ignore dto.getRole())
        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setRegNo(dto.getRegNo());
        user.setDepartment(dto.getDepartment());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.STUDENT);
        user.setEnabled(true);

        userRepository.save(user);

        return ResponseEntity.ok("Student signup successful");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequestDTO dto) {
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            return ResponseEntity.badRequest().body("Email is required");
        }

        passwordResetService.createResetTokenForEmail(dto.getEmail().trim());

        return ResponseEntity.ok("If this email exists, a reset link has been sent.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDTO dto) {
        if (dto.getToken() == null || dto.getToken().isBlank()) {
            return ResponseEntity.badRequest().body("Token is required");
        }
        if (dto.getNewPassword() == null || dto.getNewPassword().isBlank()) {
            return ResponseEntity.badRequest().body("New password is required");
        }

        passwordResetService.resetPassword(dto.getToken().trim(), dto.getNewPassword());

        return ResponseEntity.ok("Password has been reset successfully.");
    }


}
