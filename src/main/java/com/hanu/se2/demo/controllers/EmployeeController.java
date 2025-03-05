package com.hanu.se2.demo.controllers;

import com.hanu.se2.demo.models.Employee;
import com.hanu.se2.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;

    @RequestMapping(value = "/")
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

    @RequestMapping(value = "/delete/{id}")
    public String deleteEmployee(@PathVariable long id, Model model) {
        Employee employee = employeeRepository.getReferenceById(id);
        employeeRepository.delete(employee);
        return "redirect:/";
    }

    @RequestMapping("/update/{id}")
    public String updateEmployee(@PathVariable("id") long id, Model model) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        model.addAttribute("employee", employee);
        return "employee/update";
    }

    @RequestMapping("/add")
    public String addEmployee(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "employee/add";
    }

    @PostMapping("/save")
    public String saveEmployee(Employee employee) {
        // TODO: Handle file upload
        employeeRepository.save(employee);
        return "redirect:/";
    }
}
