package com.workmotion.ems.config;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.workmotion.ems.domain.EmployeeState;
import com.workmotion.ems.domain.EmployeeStateTransition;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.StateMachineService;

@SpringBootTest
public class StateMachineConfigTest {

  static StateMachine<EmployeeState, EmployeeStateTransition> stateMachine;

  @BeforeAll
  public static void createStateMachine(
      @Autowired StateMachineService<EmployeeState, EmployeeStateTransition> stateMachineService) {
    stateMachine = stateMachineService.acquireStateMachine(UUID.randomUUID().toString());

  }

  @BeforeEach
  public void restartStateMachine() {
    stateMachine.stop();
    stateMachine.start();
      stateMachine.sendEvent(EmployeeStateTransition.BEGIN_CHECK);
  }


  @Test
  void testINCheckStatusStateMachine() {
    assertTrue(stateMachine.getState().getIds().contains(EmployeeState.IN_CHECK));
    assertTrue(stateMachine.getState().getIds().contains(EmployeeState.SECURITY_CHECK_STARTED));
    assertTrue(stateMachine.getState().getIds().contains(EmployeeState.WORK_PERMIT_CHECK_STARTED));
  }

  @Test
  void testSuccessScenario() {
    stateMachine.sendEvent(EmployeeStateTransition.FINISH_SECURITY_CHECK);
    stateMachine.sendEvent(EmployeeStateTransition.COMPLETE_INITIAL_WORK_PERMIT_CHECK);
    stateMachine.sendEvent(EmployeeStateTransition.FINISH_WORK_PERMIT_CHECK);
    stateMachine.sendEvent(EmployeeStateTransition.ACTIVATE);
   assertTrue(stateMachine.getState().getIds().contains(EmployeeState.ACTIVE));
  }

  @Test
  void testFailedScenario() {
    stateMachine.sendEvent(EmployeeStateTransition.FINISH_SECURITY_CHECK);
    boolean isChanged = stateMachine.sendEvent(EmployeeStateTransition.ACTIVATE);
    assertFalse(isChanged);
  }
}
