package com.mapsa.company_management_system.model.dto;

import com.mapsa.company_management_system.model.Employee;
import com.mapsa.company_management_system.model.Leave;
import com.mapsa.company_management_system.model.enums.LeaveType;
import lombok.Getter;
import lombok.Setter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class LeaveDto {
    private String start;
    private String end;
    private Date startDate;
    private Date endDate;
    private LeaveType leaveType;
    private Boolean accepted;
    private Employee employee;

        public Boolean ifOverlaps(Leave leave){
        if(leave.getStartDate().before(this.startDate) && this.startDate.before(leave.getEndDate())){
            return true;
        } else{
            return false;
        }
    }

    public void setStartDate(String start) throws ParseException {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            this.startDate = dateFormat.parse(start);
    }

    public void setEndDate(String end) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.endDate = dateFormat.parse(end);
    }

    public Boolean ifTimeIsRight(Date start, Date end){
        return start.before(end);
    }
}
