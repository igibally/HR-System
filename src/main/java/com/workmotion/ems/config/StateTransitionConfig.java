package com.workmotion.ems.config;


import com.workmotion.ems.domain.EmployeeState;
import com.workmotion.ems.domain.EmployeeStateTransition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.data.jpa.JpaPersistingStateMachineInterceptor;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;


@Slf4j
@Configuration
@EnableStateMachineFactory
public class StateTransitionConfig extends
    StateMachineConfigurerAdapter<EmployeeState, EmployeeStateTransition> {

  @Autowired
  JpaPersistingStateMachineInterceptor<EmployeeState, EmployeeStateTransition, String> stateMachineRuntimePersister;


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
    config.withPersistence()
        .runtimePersister(stateMachineRuntimePersister);
    config.withConfiguration().autoStartup(false).listener(adapter);

  }

  @Override
  public void configure(StateMachineStateConfigurer<EmployeeState, EmployeeStateTransition> states)
      throws Exception {
    states.withStates()
        .initial(EmployeeState.ADDED)
        .fork(EmployeeState.IN_CHECK)
        .join(EmployeeState.JOIN)
        .state(EmployeeState.APPROVED)
        .end(EmployeeState.ACTIVE)
        .and()
        .withStates()
        .parent(EmployeeState.IN_CHECK)
        .initial(EmployeeState.SECURITY_CHECK_STARTED)
        .end(EmployeeState.SECURITY_CHECK_FINISHED)
        .and()
        .withStates()
        .parent(EmployeeState.IN_CHECK)
        .initial(EmployeeState.WORK_PERMIT_CHECK_STARTED)
        .state(EmployeeState.WORK_PERMIT_CHECK_PENDING_VERIFICATION)
        .end(EmployeeState.WORK_PERMIT_CHECK_FINISHED);
  }
  @Override
  public void configure(
      StateMachineTransitionConfigurer<EmployeeState, EmployeeStateTransition> transitions)
      throws Exception {
    transitions.withExternal()
        .source(EmployeeState.ADDED).target(EmployeeState.IN_CHECK)
        .event(EmployeeStateTransition.BEGIN_CHECK)
        .and()
        .withExternal()
        .source(EmployeeState.SECURITY_CHECK_STARTED).target(EmployeeState.SECURITY_CHECK_FINISHED)
        .event(EmployeeStateTransition.FINISH_SECURITY_CHECK)
        .and()
        .withExternal()
        .source(EmployeeState.WORK_PERMIT_CHECK_STARTED)
        .target(EmployeeState.WORK_PERMIT_CHECK_PENDING_VERIFICATION)
        .event(EmployeeStateTransition.COMPLETE_INITIAL_WORK_PERMIT_CHECK).and().withExternal()
        .source(EmployeeState.WORK_PERMIT_CHECK_PENDING_VERIFICATION)
        .target(EmployeeState.WORK_PERMIT_CHECK_FINISHED)
        .event(EmployeeStateTransition.FINISH_WORK_PERMIT_CHECK)
        .and()
        .withExternal()
        .source(EmployeeState.JOIN).target(EmployeeState.APPROVED)
        .and()
        .withExternal()
        .source(EmployeeState.APPROVED).target(EmployeeState.IN_CHECK)
        .event(EmployeeStateTransition.BEGIN_CHECK)
        .and()
        .withExternal()
        .source(EmployeeState.APPROVED).target(EmployeeState.ACTIVE)
        .event(EmployeeStateTransition.ACTIVATE)
        .and()
        .withFork()
        .source(EmployeeState.IN_CHECK)
        .target(EmployeeState.SECURITY_CHECK_STARTED)
        .target(EmployeeState.WORK_PERMIT_CHECK_STARTED)
        .and()
        .withJoin()
        .source((EmployeeState.SECURITY_CHECK_FINISHED))
        .source(EmployeeState.WORK_PERMIT_CHECK_FINISHED)
        .target(EmployeeState.JOIN);
  }




}