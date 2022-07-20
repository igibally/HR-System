package com.workmotion.ems.config;

import com.workmotion.ems.domain.EmployeeState;
import com.workmotion.ems.domain.EmployeeStateTransition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.data.jpa.JpaPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
import org.springframework.statemachine.service.DefaultStateMachineService;
import org.springframework.statemachine.service.StateMachineService;

@Configuration
public class JpaPersistingStateMachineConfig {

  @Bean
  public JpaPersistingStateMachineInterceptor<EmployeeState, EmployeeStateTransition, String>
  jpaPersistingStateMachineInterceptor(final JpaStateMachineRepository stateMachineRepository) {
    return new JpaPersistingStateMachineInterceptor<>(stateMachineRepository);
  }

  @Bean
  public StateMachineService<EmployeeState, EmployeeStateTransition> stateMachineService(
      final StateMachineFactory<EmployeeState, EmployeeStateTransition> stateMachineFactory,
      final StateMachinePersist<EmployeeState, EmployeeStateTransition, String> stateMachinePersist) {
    return new DefaultStateMachineService<>(stateMachineFactory, stateMachinePersist);
  }
}
