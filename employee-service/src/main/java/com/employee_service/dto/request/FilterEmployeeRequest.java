package com.employee_service.dto.request;

import com.employee_service.model.Employee.EmployeeStatus;
import lombok.Data;

@Data
public class FilterEmployeeRequest {
    private String firstName;
    private String lastName;
    private String email;
    private EmployeeStatus status;
}