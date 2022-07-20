package com.workmotion.ems.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.workmotion.ems.domain.Employee;
import com.workmotion.ems.domain.EmployeeState;
import com.workmotion.ems.dto.EmployeeDTO;
import com.workmotion.ems.repository.EmployeeRepository;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

  @InjectMocks
  EmployeeServiceImpl employeeService;

  Employee employee;
  @Captor
  ArgumentCaptor<Employee> employeeCaptor;
  @Mock
  EmployeeRepository employeeRepositoryMOCk;

  @Test
  public void create() {
    EmployeeDTO employeeDTO = EmployeeDTO.builder().age(200).name("islam mostafa")
        .phone("02 2876321").build();
    employeeService.create(employeeDTO);
    verify(employeeRepositoryMOCk).save(employeeCaptor.capture());
    Employee savedEmployee = employeeCaptor.getValue();
    assertEquals(savedEmployee.getName(), employeeDTO.getName());
  }

  @Test
  public void getAll() {
    employeeService.getAll();
    verify(employeeRepositoryMOCk).findAll();
  }

  @Test
  public void getById() {
    Long employeeId=1L;
    Employee employee = Employee.builder().age(20)
        .state(Collections.singletonList(EmployeeState.ADDED)).name("islam mostafa")
        .phone("02 2876321").id(employeeId).build();
    Mockito.when(employeeRepositoryMOCk.getReferenceById(any())).thenReturn(employee);
    employeeService.getById(employeeId);
    verify(employeeRepositoryMOCk).getReferenceById(employeeId);
  }
}