package com.mapsa.company_management_system.controller;

import com.mapsa.company_management_system.model.*;
import com.mapsa.company_management_system.model.dto.ManagerDto;
import com.mapsa.company_management_system.model.enums.EmployeeRole;
import com.mapsa.company_management_system.service.EmailService;
import com.mapsa.company_management_system.service.EmployeeService;
import com.mapsa.company_management_system.service.LeaveService;
import com.mapsa.company_management_system.service.ManagerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/manager")
@AllArgsConstructor
public class ManagerController {

    ManagerService managerService;
    LeaveService leaveService;
    EmployeeService employeeService;
    EmailService emailService;

    @GetMapping("/signup")
    public String getSignUp(Model model){
        model.addAttribute(new ManagerDto());
        return "manager_sign_up";
    }

    @PostMapping("/signup")
    public String postSignUp(@Valid ManagerDto managerDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute(managerDto);
            return "manager_sign_up";
        }
        try {
            managerDto.setEmployeeRole(EmployeeRole.MANAGER);
            managerService.save(managerDto);
            model.addAttribute("message", "Hi " + managerDto.getFirstName());
            return "redirect:/company_management_system/manager/manager_profile/" + managerDto.getUsername();
        }catch (Exception e){
            model.addAttribute("message", "There was an error signing up. Please try again.");
            model.addAttribute("managerDto",managerDto);
            return "manager_sign_up";
        }
    }

    @GetMapping("/signin")
    public String getSignIn(Model model){
        model.addAttribute("managerDto",new CompanyUser());
        return "manager_sign_in";
    }

    @PostMapping("signin")
    public String postSignIn(@Valid CompanyUser user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("managerDto",user);
            return "manager_sign_in";
        }
        if(managerService.findByUsername(user.getUsername()) == null){
            model.addAttribute("managerDto",user);
            model.addAttribute("message", "Wrong username and password. Please enter again.");
            return "manager_sign_in";
        }
        model.addAttribute("managerDto",user);
        return "redirect:/company_management_system/manager/manager_profile/" + user.getUsername();
    }

    @GetMapping("manager_profile/{manager_username}")
    public String getProfile(@PathVariable String manager_username, Model model){
        Manager manager = managerService.findByUsername(manager_username);
        ModelMapper modelMapper = new ModelMapper();
        ManagerDto managerDto = modelMapper.map(manager,ManagerDto.class);
        model.addAttribute(managerDto);
        model.addAttribute("message", "Hi " + managerDto.getFirstName());
        return "manager_profile";
    }

    @GetMapping("leave_requests/{manager_username}")
    public String getLeaveRequests(@PathVariable String manager_username, Model model){
        ArrayList<Leave> leavesList = (ArrayList<Leave>) leaveService.getAll();
        ArrayList<Leave> managerEmployeeLeaves = (ArrayList<Leave>) leavesList.stream().filter(leave -> leave.getEmployee()!=null).filter(leave -> leave.getEmployee().getManager().getUsername().equals(manager_username)).collect(Collectors.toList());
        model.addAttribute("leaves", managerEmployeeLeaves);
        return "manager_leave_requests";
    }

    @GetMapping("set_leave_status/{leave_id}")
    public String getUpdateStatus(@PathVariable Long leave_id, Model model) {
        Leave leave = leaveService.findById(leave_id).get();
            model.addAttribute("message", "Set Employee Leave Request Status.");
            model.addAttribute(leave);
            Employee employee = leave.getEmployee();
            model.addAttribute(employee);
        return "manager_leave_requests_updating";
    }

    @PostMapping("set_leave_status/{leave_id}")
    public String postUpdateStatus(@PathVariable Long leave_id,Leave leave, Model model) {
        leaveService.updateStatus(leave_id , leave.getAccepted());
        Employee employee = leaveService.findById(leave_id).get().getEmployee();
        String message = "Leave Request For " + employee.getFirstName() + " " + employee.getLastName() + " Has Been " + (leave.getAccepted()? "Accepted." : "Rejected");
        model.addAttribute("message",message);
        Manager manager = employee.getManager();
        model.addAttribute(manager);
        return "manager_leave_requests_result";
    }

    @GetMapping("send_email/{manager_username}")
    public String getSendEmail(@PathVariable String manager_username, Model model){
        Manager manager = managerService.findByUsername(manager_username);
        String from = manager.getEmailAddress();
        model.addAttribute("from", from);
        model.addAttribute(new Email());
        model.addAttribute("username", manager_username);
        model.addAttribute("role", "manager");
        return "send_email";
    }

    @PostMapping("send_email/{manager_username}")
    public String postSendEmail(@PathVariable String manager_username,@Valid Email email,BindingResult bindingResult,
                            @RequestParam(value = "attachedFile") MultipartFile attachedFile, Model model){
        if(bindingResult.hasErrors()){
            getSendEmail(manager_username, model);
        }
        Manager manager = managerService.findByUsername(manager_username);
        String from = manager.getEmailAddress();
        email.setFrom(from);
        try {
            emailService.sendEmail(email, attachedFile);
            model.addAttribute("title", "Email Sent");
            model.addAttribute("message", "Your Email Sent Successfully.");
            model.addAttribute("username", manager_username);
            model.addAttribute("role", "manager");
            return "send_email_result";
        }catch (Exception e){
            model.addAttribute("title", "Email Not Sent");
            model.addAttribute("message", "There Was An Error Sending Your Email.");
            model.addAttribute("username", manager_username);
            model.addAttribute("role", "manager");
            e.printStackTrace();
            return "send_email_result";
        }

    }

}
