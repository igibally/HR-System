package com.workmotion.ems.controller;


import com.workmotion.ems.dto.ChangeStateDTO;
import com.workmotion.ems.dto.EmployeeDTO;
import com.workmotion.ems.service.EmployeStateTransitionService;
import com.workmotion.ems.service.EmployeeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/employees")

public class EmployeeController {

  @Autowired
  EmployeeService employeeService;

  @Autowired
  EmployeStateTransitionService employeeStateTransitionService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createEmployee(@RequestBody EmployeeDTO employeeDTO) {
    employeeService.create(employeeDTO);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<EmployeeDTO> getAllEmployee() {
    return employeeService.getAll();
  }

  @PatchMapping("/{id}/state")
  @ResponseStatus(HttpStatus.OK)
  public void changeState(@RequestBody ChangeStateDTO changeStateDto, @PathVariable long id) {
    employeeStateTransitionService.transitState(id, changeStateDto.getStateTransition());
  }


}
