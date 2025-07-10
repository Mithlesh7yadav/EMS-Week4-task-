// EmployeeController.java
package com.UpgradedEMS.controller;

import com.UpgradedEMS.entity.*;
import com.UpgradedEMS.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<List<Employee>> all() { return ResponseEntity.ok(service.all()); }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<Employee> one(@PathVariable Long id) { return ResponseEntity.ok(service.byId(id)); }

    /* manager‑only team view */
    @GetMapping("/team")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<Employee>> myTeam(@AuthenticationPrincipal Employee me) {
        return ResponseEntity.ok(service.teamOf(me.getId()));
    }

    /* create → ADMIN only */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Employee> create(@RequestBody Employee e) {
        return ResponseEntity.status(201).body(service.create(e));
    }

    /* update → ADMIN or MANAGER of that employee */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('MANAGER') and #id == principal.id)")
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee e) {
        return ResponseEntity.ok(service.update(id, e));
    }

    /* delete → ADMIN only */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
