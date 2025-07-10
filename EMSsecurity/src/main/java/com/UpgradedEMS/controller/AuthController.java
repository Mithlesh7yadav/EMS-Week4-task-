package com.UpgradedEMS.controller;

import com.UpgradedEMS.dto.*;
import com.UpgradedEMS.entity.*;
import com.UpgradedEMS.repository.EmployeeRepository;
import com.UpgradedEMS.security.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final EmployeeRepository repo;
    private final AuthenticationManager authManager;
    private final PasswordEncoder enc;
    private final JwtUtil jwt;

    /* -------- login -------- */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest req) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username(), req.password()));
        UserDetails user = repo.findByUsername(req.username()).get();
        return ResponseEntity.ok(new AuthResponse(jwt.generateToken(user)));
    }

    /* -------- self‑register (role = EMPLOYEE) -------- */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthRequest req) {
        if (repo.findByUsername(req.username()).isPresent())
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        Employee saved = repo.save(
                new Employee(null, req.username(), enc.encode(req.password()),
                        null, null, 0, 0, Role.ROLE_EMPLOYEE, null, null));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(jwt.generateToken(saved)));
    }

    /* -------- promote user (ADMIN ‑ only) -------- */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/promote/{id}")
    public ResponseEntity<Employee> promote(@PathVariable Long id,
                                            @RequestParam String role) {
        Role r = Role.valueOf(role);
        Employee e = repo.findById(id).orElseThrow();
        e.setRole(r);
        return ResponseEntity.ok(repo.save(e));
    }
}
