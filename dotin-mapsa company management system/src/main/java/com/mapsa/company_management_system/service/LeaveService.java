package com.mapsa.company_management_system.service;

import com.mapsa.company_management_system.model.Leave;
import com.mapsa.company_management_system.model.dto.LeaveDto;
import com.mapsa.company_management_system.model.enums.LeaveRequestCheck;
import com.mapsa.company_management_system.repository.LeaveRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LeaveService {

    private LeaveRepository leaveRepository;

    public Leave save(LeaveDto leaveDto){
        return leaveRepository.save(mapObject(leaveDto));
    }

    public Leave mapObject(LeaveDto leaveDto){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(leaveDto,Leave.class);
    }

    public List<Leave> getAll(){
        return leaveRepository.findAll();
    }

    public void updateStatus(Long id, Boolean status){
        leaveRepository.updateStatus(id,status);
    }

    public List<Leave> findAllByEmployeeUsername(String username){
        return leaveRepository.findAllByEmployeeUsername(username);
    }

    public Optional<Leave> findById(Long id){
        return leaveRepository.findById(id);
    }

    public void deleteById(Long id){
        leaveRepository.deleteById(id);
    }

    public LeaveRequestCheck checkValidity(LeaveDto leaveDto, List<Leave> allEmployeeLeaves, Model model){
        final int requestLimit = 10;
        if(leaveDto.ifTimeIsRight(leaveDto.getStartDate(),leaveDto.getEndDate())) {
            if(allEmployeeLeaves.size() > requestLimit){
                return LeaveRequestCheck.LIMIT_EXCEED;
            }
            for (Leave leave:allEmployeeLeaves) {
                if(leaveDto.ifOverlaps(leave)) {
                    model.addAttribute("overlapped_leave",leave);
                    return LeaveRequestCheck.OVERLAP;
                }
            }
        }else{
            return LeaveRequestCheck.WRONG_DATE;
        }
        return LeaveRequestCheck.OK;
    }
}
