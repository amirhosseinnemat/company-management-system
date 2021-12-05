package com.mapsa.company_management_system.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@Component
public class Email {

    @NotBlank(message = "Email must have a sender.")
    @javax.validation.constraints.Email
    private String from;

    @NotBlank(message = "Email must have a receiver.")
    @javax.validation.constraints.Email
    private String to;

    private List<String> cc;

    private List<String> bcc;

    private String subject = "no subject";

    @NotBlank(message = "Email must have a body.")
    private String body;

}
