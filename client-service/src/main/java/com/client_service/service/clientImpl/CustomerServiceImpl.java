package com.client_service.service.clientImpl;

import com.client_service.dto.request.CustomerRequest;
import com.client_service.dto.response.CustomerResponse;
import com.client_service.models.Customer;
import com.client_service.repository.CustomerRepository;
import com.client_service.service.clientDAO.CustomerServiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerServiceDAO {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email ya está en uso");
        }

        Customer customer = Customer.builder()
                .userCode(request.getUserCode())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhoneNumber())
                .registrationDate(LocalDate.now())
                .purchaseCount(0)
                .totalSpent(0.0)
                .build();

        customerRepository.save(customer);

        return toResponse(customer);
    }

    @Override
    public CustomerResponse getCustomerByUserCode(String userCode) {
        Customer customer = customerRepository.findByUserCode(userCode)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        return toResponse(customer);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getUserCode(),
                customer.getFirstName() + " " + customer.getLastName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getRegistrationDate(),
                customer.getPurchaseCount(),
                customer.getTotalSpent()
        );
    }
}
