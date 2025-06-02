package com.employee_service.service.dao;

import com.employee_service.dto.request.CreateEmployeeRequest;
import com.employee_service.dto.request.FilterEmployeeRequest;
import com.employee_service.dto.request.UpdateEmployeeRequest;
import com.employee_service.dto.response.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    EmployeeResponse createEmployee(CreateEmployeeRequest request);
    EmployeeResponse getEmployeeById(Long id);
    List<EmployeeResponse> getAllEmployees();
    List<EmployeeResponse> filterEmployees(FilterEmployeeRequest filter);
    EmployeeResponse updateEmployee(Long id, UpdateEmployeeRequest request);
    void deleteEmployee(Long id);
}
