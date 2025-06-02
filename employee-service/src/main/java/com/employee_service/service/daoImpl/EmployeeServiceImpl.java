package com.employee_service.service.daoImpl;

import com.employee_service.dto.request.*;
import com.employee_service.dto.response.EmployeeResponse;
import com.employee_service.model.Employee;
import com.employee_service.repository.EmployeeRepository;
import com.employee_service.service.dao.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import jakarta.persistence.criteria.Predicate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeResponse createEmployee(CreateEmployeeRequest request) {
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        Employee employee = Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .hireDate(request.getHireDate())
                .status(request.getStatus())
                .build();

        return EmployeeResponse.fromEntity(employeeRepository.save(employee));
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {
        return EmployeeResponse.fromEntity(
                employeeRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Employee not found"))
        );
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(EmployeeResponse::fromEntity)
                .toList();
    }

    @Override
    public List<EmployeeResponse> filterEmployees(FilterEmployeeRequest filter) {
        Specification<Employee> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getFirstName() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("firstName")),
                        "%" + filter.getFirstName().toLowerCase() + "%"
                ));
            }
            if (filter.getLastName() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("lastName")),
                        "%" + filter.getLastName().toLowerCase() + "%"
                ));
            }
            if (filter.getEmail() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("email")),
                        "%" + filter.getEmail().toLowerCase() + "%"
                ));
            }
            if (filter.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filter.getStatus()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return employeeRepository.findAll(spec).stream()
                .map(EmployeeResponse::fromEntity)
                .toList();
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, UpdateEmployeeRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Optional.ofNullable(request.getFirstName()).ifPresent(employee::setFirstName);
        Optional.ofNullable(request.getLastName()).ifPresent(employee::setLastName);

        Optional.ofNullable(request.getEmail()).ifPresent(email -> {
            if (!email.equals(employee.getEmail())) {
                if (employeeRepository.existsByEmail(email)) {
                    throw new RuntimeException("Email already in use");
                }
                employee.setEmail(email);
            }
        });

        Optional.ofNullable(request.getPhoneNumber()).ifPresent(employee::setPhoneNumber);
        Optional.ofNullable(request.getHireDate()).ifPresent(employee::setHireDate);
        Optional.ofNullable(request.getStatus()).ifPresent(employee::setStatus);

        return EmployeeResponse.fromEntity(employeeRepository.save(employee));
    }

    @Override
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found");
        }
        employeeRepository.deleteById(id);
    }
}