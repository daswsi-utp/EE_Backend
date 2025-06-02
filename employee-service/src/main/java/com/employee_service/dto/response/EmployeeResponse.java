package com.employee_service.dto.response;

import com.employee_service.model.Employee.EmployeeStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate hireDate;
    private EmployeeStatus status;
}
