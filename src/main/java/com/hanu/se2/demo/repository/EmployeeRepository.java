package com.hanu.se2.demo.repository;

import com.hanu.se2.demo.models.Company;
import com.hanu.se2.demo.models.Employee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByCompany(Company company, Sort sort);
}