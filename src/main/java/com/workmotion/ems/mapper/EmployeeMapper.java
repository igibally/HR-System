package com.workmotion.ems.mapper;

import com.workmotion.ems.domain.Employee;
import com.workmotion.ems.dto.EmployeeDTO;


public class EmployeeMapper {

  public static Employee makeEmployee(EmployeeDTO employeeDTO) {

    return Employee.builder().address(employeeDTO.getAddress()).age(employeeDTO.getAge())
        .name(employeeDTO.getName()).phone(employeeDTO.getPhone()).state(employeeDTO.getState())
        .build();
  }

  public static EmployeeDTO makeEmployeeDTO(Employee employee) {
    return EmployeeDTO.builder().address(employee.getAddress()).age(employee.getAge())
        .name(employee.getName()).phone(employee.getPhone()).state(employee.getState())
        .id(employee.getId().toString()).build();
  }


}
