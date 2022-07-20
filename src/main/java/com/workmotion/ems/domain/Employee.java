package com.workmotion.ems.domain;

import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
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
  @ElementCollection
  private List<EmployeeState> state;
  private String name;
  private Integer age;
  private String phone;
  private String address;
}
