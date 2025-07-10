package com.UpgradedEMS.repository;

import com.UpgradedEMS.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DepartmentRepository extends JpaRepository<Department, Long> { }