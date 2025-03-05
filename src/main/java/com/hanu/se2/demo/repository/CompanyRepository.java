package com.hanu.se2.demo.repository;

import com.hanu.se2.demo.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}