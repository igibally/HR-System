package com.workmotion.ems.util;

import com.workmotion.ems.domain.Employee;
import com.workmotion.ems.domain.EmployeeState;
import com.workmotion.ems.domain.EmployeeStateTransition;
import com.workmotion.ems.repository.EmployeeRepository;
import com.workmotion.ems.service.EmployeeStateTransitionServiceImpl;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;


@Component
public class EmployeeStateChangeListener extends
    StateMachineInterceptorAdapter<EmployeeState, EmployeeStateTransition> {

  @Autowired
  private EmployeeRepository employeeRepository;

  @Override
  public void preStateChange(State<EmployeeState, EmployeeStateTransition> state,
      Message<EmployeeStateTransition> message,
      Transition<EmployeeState, EmployeeStateTransition> transition,
      StateMachine<EmployeeState, EmployeeStateTransition> stateMachine) {
    Optional.ofNullable(message).ifPresent(msg -> {
      Optional.ofNullable((Long) msg.getHeaders().getOrDefault(
          EmployeeStateTransitionServiceImpl.EMPLOYEE_ID_HEADER, -1L))
          .ifPresent(employeeId -> {
            Employee employee = employeeRepository.getReferenceById(employeeId);
            employee.setState(state.getId());
            employeeRepository.save(employee);
          });
    });

  }
}
