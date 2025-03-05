package com.hanu.se2.demo.controllers;

import com.hanu.se2.demo.models.Company;
import com.hanu.se2.demo.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    CompanyRepository companyRepository;

    @GetMapping("/list")
    public String getAllCompany(Model model) {
        model.addAttribute("companies", companyRepository.findAll());
        return "company/list";
    }

    @GetMapping("/{id}")
    public String getCompanyById(@PathVariable("id") Long id, Model model) {
        Company company = companyRepository.getById(id);
        model.addAttribute("company", company);
        model.addAttribute("employees", company.getEmployees());
        return "company/detail";
    }

    @GetMapping("/add")
    public String addCompany(Model model) {
        model.addAttribute("company", new Company());
        return "company/add";
    }

    @GetMapping("/update/{id}")
    public String updateCompany(@PathVariable("id") Long id, Model model) {
        model.addAttribute("company", companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid company Id:" + id)));
        return "company/update";
    }

    @PostMapping("/save")
    public String saveCompany(Company company) {
        companyRepository.save(company);
        return "redirect:/company/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCompany(@PathVariable("id") Long id) {
        if (companyRepository.findById(id).isPresent()) {
            companyRepository.delete(companyRepository.findById(id).get());
        }
        return "redirect:/company/list";
    }
}