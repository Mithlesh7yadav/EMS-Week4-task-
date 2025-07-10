package com.UpgradedEMS.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "employees")
public class Employee implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;          // login id

    @JsonIgnore
    private String password;

    private String name;
    private String project;
    private int age;
    private double salary;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_EMPLOYEE;

    /* ─── relationships ──────────────────────────────── */

    // self‑reference → manager
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    /* ─── UserDetails contract ───────────────────────── */

    @JsonIgnore
    @Override public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role::name);
    }
    @JsonIgnore @Override public boolean isAccountNonExpired() { return true; }
    @JsonIgnore @Override public boolean isAccountNonLocked() { return true; }
    @JsonIgnore @Override public boolean isCredentialsNonExpired() { return true; }
    @JsonIgnore @Override public boolean isEnabled() { return true; }
}
