package com.hanu.se2.demo.controllers;

import com.hanu.se2.demo.models.Employee;
import com.hanu.se2.demo.repository.CompanyRepository;
import com.hanu.se2.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/employee") // Add this base mapping
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;

    // Add to EmployeeController.java
    @Autowired
    CompanyRepository companyRepository;

    @RequestMapping(value = "/list")
    public String getAllEmployee(Model model) {
        List<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        return "employee/index";
    }

    @RequestMapping(value = "/detail/{id}")
    public String getEmployeeDetail(@PathVariable long id, Model model) {
        Employee employee = employeeRepository.getReferenceById(id);
        model.addAttribute("employee", employee);
        return "employee/detail";
    }

    // Update addEmployee method
    @RequestMapping("/add")
    public String addEmployee(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("companies", companyRepository.findAll());
        return "employee/add";
    }

    // Update updateEmployee method
    @RequestMapping("/update/{id}")
    public String updateEmployee(@PathVariable("id") long id, Model model) {
        model.addAttribute("employee", employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id)));
        model.addAttribute("companies", companyRepository.findAll());
        return "employee/update";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteEmployee(@PathVariable("id") long id) {
        if (employeeRepository.findById(id).isPresent()) {
            Employee employee = employeeRepository.findById(id).get();
            // Optional: check if employee is null
            employeeRepository.delete(employee);
        }
        return "redirect:/employee/list";
    }

    @PostMapping("/save")
    public String saveEmployee(Employee employee) {
        employeeRepository.save(employee);
        return "redirect:/employee/list/";
    }
}
