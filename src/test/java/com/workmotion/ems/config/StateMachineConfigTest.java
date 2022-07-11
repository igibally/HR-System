package com.workmotion.ems.config;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.workmotion.ems.domain.EmployeeState;
import com.workmotion.ems.domain.EmployeeStateTransition;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

@SpringBootTest
public class StateMachineConfigTest {

  @Autowired
  StateMachineFactory<EmployeeState, EmployeeStateTransition> factory;

  @Test
  void testNewStateMachine() {
    StateMachine<EmployeeState, EmployeeStateTransition> stateMachine = factory
        .getStateMachine(UUID.randomUUID());
    stateMachine.start();
    stateMachine.sendEvent(EmployeeStateTransition.BEGIN_CHECK);
    assertEquals(stateMachine.getState().getId(), EmployeeState.IN_CHECK);
    stateMachine.sendEvent(EmployeeStateTransition.APPROVE);
    assertEquals(stateMachine.getState().getId(), EmployeeState.APPROVED);
    stateMachine.sendEvent(EmployeeStateTransition.ACTIVATE);
    assertEquals(stateMachine.getState().getId(), EmployeeState.ACTIVE);
  }

}
