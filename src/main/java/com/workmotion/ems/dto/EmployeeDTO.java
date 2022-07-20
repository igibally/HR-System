package com.workmotion.ems.dto;

import com.workmotion.ems.domain.EmployeeState;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class EmployeeDTO {

  private List<EmployeeState> state;
  private String name;
  private Integer age;
  private String phone;
  private String address;
  private String id;


}
