package com.workmotion.ems.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import com.workmotion.ems.domain.Employee;
import com.workmotion.ems.domain.EmployeeState;
import com.workmotion.ems.domain.EmployeeStateTransition;
import com.workmotion.ems.repository.EmployeeRepository;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class EmployeeStateTransitionServiceImplTest {

  @Mock
  EmployeeRepository employeeRepositoryMOCk;

  @Autowired
  @InjectMocks
  EmployeeStateChangeInterceptor employeeStateChangeInterceptor;

  @InjectMocks
  @Autowired
  EmployeeStateTransitionServiceImpl employeeStateTransitionService;
  Employee employee;


  @Test
  public void transitState() {
    Long employeeId = 1L;
    Employee employee = Employee.builder().age(20)
        .state(Collections.singletonList(EmployeeState.ADDED)).name("islam mostafa")
        .phone("02 2876321").id(employeeId).build();
    Mockito.when(employeeRepositoryMOCk.getReferenceById(any())).thenReturn(employee);
    Employee savedEmployee = Employee.builder().age(20)
        .state(Collections.singletonList(EmployeeState.IN_CHECK))
        .name("islam mostafa").phone("02 2876321").id(employeeId).build();
    Mockito.when(employeeRepositoryMOCk.save(any())).thenReturn(savedEmployee);
    StateMachine<EmployeeState, EmployeeStateTransition> sm =
        employeeStateTransitionService
            .transitState(employeeId, EmployeeStateTransition.BEGIN_CHECK);
    assertTrue(sm.getState().getIds().contains(EmployeeState.IN_CHECK));
    assertTrue(sm.getState().getIds().contains(EmployeeState.SECURITY_CHECK_STARTED));
    assertTrue(sm.getState().getIds().contains(EmployeeState.WORK_PERMIT_CHECK_STARTED));
  }
}
