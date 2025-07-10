// DepartmentService.java
package com.UpgradedEMS.service;

import com.UpgradedEMS.entity.Department;
import com.UpgradedEMS.exception.ResourceNotFoundException;
import com.UpgradedEMS.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository repo;

    public Department save(Department d) { return repo.save(d); }
    public List<Department> all() { return repo.findAll(); }
    public Department byId(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Dept " + id));
    }
    public void delete(Long id) { repo.deleteById(id); }
}
