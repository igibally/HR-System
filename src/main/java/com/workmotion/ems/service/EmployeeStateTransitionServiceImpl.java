package com.workmotion.ems.service;

import com.workmotion.ems.domain.Employee;
import com.workmotion.ems.domain.EmployeeState;
import com.workmotion.ems.domain.EmployeeStateTransition;
import com.workmotion.ems.repository.EmployeeRepository;
import com.workmotion.ems.util.EmployeeStateChangeListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;


@Service
public class EmployeeStateTransitionServiceImpl implements EmployeStateTransitionService {

  @Autowired
  private EmployeeRepository employeeRepository;
  public static final String EMPLOYEE_ID_HEADER = "employee_id";

  @Autowired
  private EmployeeStateChangeListener employeeStateChangeListener;


  @Autowired
  private StateMachineFactory<EmployeeState, EmployeeStateTransition> stateMachineFactory;

  @Override
  public StateMachine<EmployeeState, EmployeeStateTransition> transitState(Long EmployeeId,
      EmployeeStateTransition stateTransition) {
    StateMachine<EmployeeState, EmployeeStateTransition> sm = build(EmployeeId);
    sendEvent(EmployeeId, sm, stateTransition);
    return sm;
  }


  private void sendEvent(Long paymentId, StateMachine<EmployeeState, EmployeeStateTransition> sm,
      EmployeeStateTransition stateTransition) {
    Message msg = MessageBuilder.withPayload(stateTransition)
        .setHeader(EMPLOYEE_ID_HEADER, paymentId).build();
    sm.sendEvent(msg);

  }

  private StateMachine<EmployeeState, EmployeeStateTransition> build(Long paymentId) {
    Employee employee = employeeRepository.getReferenceById(paymentId);
    StateMachine<EmployeeState, EmployeeStateTransition> sm =
        stateMachineFactory.getStateMachine(Long.toString(employee.getId()));
    sm.stop();
    sm.getStateMachineAccessor().doWithAllRegions(sma -> {
      sma.addStateMachineInterceptor(employeeStateChangeListener);
      sma.resetStateMachine(
          new DefaultStateMachineContext<>(employee.getState(), null, null, null));
    });
    sm.start();
    return sm;
  }
}
