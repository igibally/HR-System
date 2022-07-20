package com.workmotion.ems.service;

import com.workmotion.ems.dto.EmployeeDTO;
import java.util.List;


public interface EmployeeService {

   public void create(EmployeeDTO employeeDTO);

   public List<EmployeeDTO> getAll();

   public EmployeeDTO getById(Long id);
}
