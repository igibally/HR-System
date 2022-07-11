package com.workmotion.ems.config;


import com.workmotion.ems.domain.EmployeeState;
import com.workmotion.ems.domain.EmployeeStateTransition;
import java.util.EnumSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;


@Slf4j
@Configuration
@EnableStateMachineFactory
public class StateTransitionConfig extends
    StateMachineConfigurerAdapter<EmployeeState, EmployeeStateTransition> {

  @Override
  public void configure(StateMachineStateConfigurer<EmployeeState, EmployeeStateTransition> states)
      throws Exception {
    states.withStates().initial(EmployeeState.ADDED).
        states(EnumSet.allOf(EmployeeState.class))
        .end(EmployeeState.ACTIVE);
  }

  @Override
  public void configure(
      StateMachineTransitionConfigurer<EmployeeState, EmployeeStateTransition> transitions)
      throws Exception {
    transitions.withExternal().source(EmployeeState.ADDED).target(EmployeeState.IN_CHECK).event(
        EmployeeStateTransition.BEGIN_CHECK)
        .and().withExternal().source(EmployeeState.IN_CHECK)
        .target(EmployeeState.APPROVED).event(EmployeeStateTransition.APPROVE)
        .and().withExternal().source(EmployeeState.APPROVED)
        .target(EmployeeState.ACTIVE).event(EmployeeStateTransition.ACTIVATE)
        .and().withExternal().source(EmployeeState.APPROVED).target(EmployeeState.IN_CHECK)
        .event(EmployeeStateTransition.UNAPPROVE);
  }

  @Override
  public void configure(
      StateMachineConfigurationConfigurer<EmployeeState, EmployeeStateTransition> config)
      throws Exception {

    StateMachineListenerAdapter<EmployeeState, EmployeeStateTransition> adapter = new StateMachineListenerAdapter<>() {
      @Override
      public void stateChanged(State<EmployeeState, EmployeeStateTransition> from,
          State<EmployeeState, EmployeeStateTransition> to) {
        log.info(String.format("stateChanged(from:%s, to:%s)", from, to));
      }
    };
    config.withConfiguration().autoStartup(false).listener(adapter);

  }
}