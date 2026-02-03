//                          FOR MIGRATING OLD SIMPLE PASSWORDS TO HASHED PASSWORDS
//
// package com.employee.management.migration;
//
//import com.employee.management.entity.Employee;
//import com.employee.management.repository.EmployeeRepo;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class PasswordMigrationRunner implements CommandLineRunner {
//
//    private final EmployeeRepo repo;
//    private final PasswordEncoder passwordEncoder;
//
//    public PasswordMigrationRunner(EmployeeRepo repo,
//                                   PasswordEncoder passwordEncoder) {
//        this.repo = repo;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public void run(String... args) {
//
//        List<Employee> employees = repo.findAll();
//
//        for (Employee emp : employees) {
//
//            String pwd = emp.getPassword();
//
//            // Skip already-hashed passwords
//            if (pwd != null && !pwd.startsWith("$2a$")) {
//
//                emp.setPassword(passwordEncoder.encode(pwd));
//                repo.save(emp);
//
//                System.out.println("Migrated password for: " + emp.getWorkEmail());
//            }
//        }
//    }
//}

