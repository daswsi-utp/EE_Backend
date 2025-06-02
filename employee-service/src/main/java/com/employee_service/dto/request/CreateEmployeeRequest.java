package com.employee_service.dto.request;

import com.employee_service.model.Employee.EmployeeStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateEmployeeRequest {
    @NotBlank @Size(min = 2, max = 50)
    private String firstName;

    @NotBlank @Size(min = 2, max = 50)
    private String lastName;

    @NotBlank @Email @Size(max = 100)
    private String email;

    @Pattern(regexp = "^\\+?[0-9\\s-]{10,20}$")
    private String phoneNumber;

    @PastOrPresent
    private LocalDate hireDate;

    @NotNull
    private EmployeeStatus status;
}
