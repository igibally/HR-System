package com.workmotion.ems.service;


import com.workmotion.ems.domain.Employee;
import com.workmotion.ems.domain.EmployeeState;
import com.workmotion.ems.dto.EmployeeDTO;
import com.workmotion.ems.mapper.EmployeeMapper;
import com.workmotion.ems.repository.EmployeeRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EmployeeServiceImpl implements EmployeeService {

  @Autowired
  private EmployeeRepository employeeRepository;

  @Override
  public void create(EmployeeDTO employeeDTO) {
    Employee employee = EmployeeMapper.makeEmployee(employeeDTO);
    employee.setState(EmployeeState.ADDED);
    employeeRepository.save(employee);
  }

  @Override
  public List<EmployeeDTO> getAll() {
    return employeeRepository.findAll().stream().map(emp -> EmployeeMapper.makeEmployeeDTO(emp))
        .collect(Collectors.toList());
  }
}
