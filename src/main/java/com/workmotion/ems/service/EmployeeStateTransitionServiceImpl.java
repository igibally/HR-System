package com.workmotion.ems.service;

import com.workmotion.ems.domain.Employee;
import com.workmotion.ems.domain.EmployeeState;
import com.workmotion.ems.domain.EmployeeStateTransition;
import com.workmotion.ems.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Service;


@Service
public class EmployeeStateTransitionServiceImpl implements EmployeStateTransitionService {

  @Autowired
  private EmployeeRepository employeeRepository;
  public static final String EMPLOYEE_ID_HEADER = "employee_id";

  @Autowired
  private EmployeeStateChangeInterceptor employeeStateChangeInterceptor;


  @Autowired
  private StateMachineService<EmployeeState, EmployeeStateTransition> stateMachineService;


  @Autowired
  private StateMachineFactory<EmployeeState, EmployeeStateTransition> stateMachineFactory;

  @Override
  public StateMachine<EmployeeState, EmployeeStateTransition> transitState(Long EmployeeId,
      EmployeeStateTransition stateTransition) {
    StateMachine<EmployeeState, EmployeeStateTransition> sm = build(EmployeeId);
    sendEvent(EmployeeId, sm, stateTransition);
    return sm;
  }


  private void sendEvent(Long employeeId, StateMachine<EmployeeState, EmployeeStateTransition> sm,
      EmployeeStateTransition stateTransition) {
    Message msg = MessageBuilder.withPayload(stateTransition)
        .setHeader(EMPLOYEE_ID_HEADER, employeeId).build();
    sm.sendEvent(msg);

  }

  private StateMachine<EmployeeState, EmployeeStateTransition> build(Long paymentId) {
    Employee employee = employeeRepository.getReferenceById(paymentId);
    StateMachine<EmployeeState, EmployeeStateTransition> sm =
        stateMachineService.acquireStateMachine(Long.toString(employee.getId()));
    sm.getStateMachineAccessor().doWithAllRegions(sma -> {
      sma.addStateMachineInterceptor(employeeStateChangeInterceptor);
    });
    return sm;
  }
}
