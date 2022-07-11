package com.workmotion.ems.service;

import com.workmotion.ems.domain.EmployeeState;
import com.workmotion.ems.domain.EmployeeStateTransition;
import org.springframework.statemachine.StateMachine;

public interface EmployeStateTransitionService {

  StateMachine<EmployeeState, EmployeeStateTransition> transitState(Long EmployeeId,
      EmployeeStateTransition stateTransition);

}
