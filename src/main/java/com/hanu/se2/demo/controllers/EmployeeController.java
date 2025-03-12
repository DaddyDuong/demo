package com.hanu.se2.demo.controllers;

import com.hanu.se2.demo.models.Company;
import com.hanu.se2.demo.models.Employee;
import com.hanu.se2.demo.repository.CompanyRepository;
import com.hanu.se2.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CompanyRepository companyRepository;

    @RequestMapping(value = "/list")
    public String getAllEmployee(
            @RequestParam(value = "company", required = false, defaultValue = "0") Long comId,
            @RequestParam(value = "sort", required = false, defaultValue = "0") int sortMode,
            Model model) {

        Sort.Direction sortOrder = Sort.Direction.DESC;
        String sortColumn = "id";

        if (sortMode == 1 || sortMode == 2) {
            sortOrder = Sort.Direction.ASC;
        }
        if (sortMode == 2 || sortMode == 3) {
            sortColumn = "name";
        }

        List<Employee> employees = null;
        if (comId != 0) {
            Optional<Company> comp = companyRepository.findById(comId);
            if (comp.isPresent()) {
                employees = employeeRepository.findByCompany(
                        comp.get(),
                        Sort.by(sortOrder, sortColumn)
                );
            }
        }

        if (employees == null) {
            employees = employeeRepository.findAll(
                    Sort.by(sortOrder, sortColumn)
            );
        }

        List<Company> companies = companyRepository.findAll();

        model.addAttribute("employees", employees);
        model.addAttribute("companies", companies);
        model.addAttribute("comId", comId);
        model.addAttribute("sortMode", sortMode);

        return "employee/index";
    }

    @RequestMapping(value = "/detail/{id}")
    public String getEmployeeDetail(@PathVariable long id, Model model) {
        Employee employee = employeeRepository.getReferenceById(id);
        model.addAttribute("employee", employee);
        return "employee/detail";
    }

    @RequestMapping("/add")
    public String addEmployee(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("companies", companyRepository.findAll());
        return "employee/add";
    }

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
            employeeRepository.delete(employee);
        }
        return "redirect:/employee/list";
    }

    @PostMapping("/save")
    public String saveEmployee(Employee employee) {
        employeeRepository.save(employee);
        return "redirect:/employee/list";
    }
}