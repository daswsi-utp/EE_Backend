package com.employee_service.dto.request;

import com.employee_service.model.Employee;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateEmployeeRequest {
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    private String email;

    @Pattern(regexp = "^\\+?[0-9\\s]{10,15}$", message = "Phone number should be valid (10-15 digits)")
    private String phoneNumber;

    @PastOrPresent(message = "Hire date cannot be in the future")
    private LocalDate hireDate;

    private Employee.EmployeeStatus status;
}
