package com.mapsa.company_management_system.model;

import com.mapsa.company_management_system.model.enums.LeaveType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.text.DateFormat;
import java.util.Date;

@NoArgsConstructor
@Entity
@Component
@Table(name = "leaves")
@Getter
@Setter
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    private Boolean accepted;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "employee_id")
    private Employee employee;


}
