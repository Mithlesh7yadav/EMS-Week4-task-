package com.UpgradedEMS.repository;

import com.UpgradedEMS.entity.Employee;
import com.UpgradedEMS.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUsername(String username);
    List<Employee> findByManager_Id(Long managerId);
    long countByRole(Role role);
}
