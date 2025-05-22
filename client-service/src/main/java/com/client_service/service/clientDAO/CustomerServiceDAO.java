package com.client_service.service.clientDAO;

import com.client_service.dto.request.CustomerRequest;
import com.client_service.dto.response.CustomerResponse;
import java.util.List;

public interface CustomerServiceDAO {
    CustomerResponse createCustomer(CustomerRequest request);
    CustomerResponse getCustomerByUserCode(String userCode);
    List<CustomerResponse> getAllCustomers();
}
