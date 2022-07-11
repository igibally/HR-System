package com.workmotion.ems.domain;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {

  @Id
  @GeneratedValue
  private Long id;
  @Enumerated
  private EmployeeState state;
  private String name;
  private Integer age;
  private String phone;
  private String address;
}
