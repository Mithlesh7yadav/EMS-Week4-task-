// EmployeeService.java
package com.UpgradedEMS.service;

import com.UpgradedEMS.entity.*;
import com.UpgradedEMS.exception.ResourceNotFoundException;
import com.UpgradedEMS.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repo;
    private final PasswordEncoder enc;

    /* CRUD (admin‑only for create/delete) */
    public Employee create(Employee e) {
        e.setPassword(enc.encode(e.getPassword()));
        return repo.save(e);
    }
    public List<Employee> all() { return repo.findAll(); }
    public Employee byId(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Emp " + id));
    }
    public Employee update(Long id, Employee data) {
        Employee e = byId(id);
        e.setName(data.getName());
        e.setProject(data.getProject());
        e.setAge(data.getAge());
        e.setSalary(data.getSalary());
        e.setDepartment(data.getDepartment());
        e.setManager(data.getManager());
        return repo.save(e);
    }
    public void delete(Long id) { repo.deleteById(id); }

    /* manager's team */
    public List<Employee> teamOf(Long managerId) { return repo.findByManager_Id(managerId); }

    /* promotion */
    public Employee promote(Long id, Role newRole) {
        Employee e = byId(id);
        e.setRole(newRole);
        return repo.save(e);
    }
}
