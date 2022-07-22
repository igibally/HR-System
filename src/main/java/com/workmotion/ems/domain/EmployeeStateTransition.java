package com.workmotion.ems.domain;

public enum EmployeeStateTransition {
  BEGIN_CHECK,
  APPROVE,
  UNAPPROVE,
  FINISH_SECURITY_CHECK,
  FINISH_WORK_PERMIT_CHECK,
  COMPLETE_INITIAL_WORK_PERMIT_CHECK,
  ACTIVATE
}
