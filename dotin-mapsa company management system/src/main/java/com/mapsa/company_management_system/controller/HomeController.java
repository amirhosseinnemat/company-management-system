package com.mapsa.company_management_system.controller;

import com.mapsa.company_management_system.model.Employee;
import com.mapsa.company_management_system.model.dto.EmployeeDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model){
        String message = "Dotin Company Management System!";
        model.addAttribute("message", message);
        return "home";
    }

    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("message", "Choose One:");
        return "signup";
    }

    @GetMapping("/signin")
    public String signin(Model model){
        model.addAttribute("message", "Choose One:");
        return "signin";
    }




}
