// DataInitializer.java
package com.UpgradedEMS.config;

import com.UpgradedEMS.entity.*;
import com.UpgradedEMS.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final EmployeeRepository repo;
    private final PasswordEncoder enc;

    @Override
    public void run(String... args) {
        if (repo.countByRole(Role.ROLE_ADMIN) == 0) {
            repo.save(new Employee(null, "admin", enc.encode("admin123"),
                    "System Admin", null, 0, 0,
                    Role.ROLE_ADMIN, null, null));
        }
    }
}
