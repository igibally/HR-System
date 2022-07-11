package com.workmotion.ems.service;

import com.workmotion.ems.dto.EmployeeDTO;
import java.util.List;


public interface EmployeeService {

  void create(EmployeeDTO employeeDTO);

  List<EmployeeDTO> getAll();
}
