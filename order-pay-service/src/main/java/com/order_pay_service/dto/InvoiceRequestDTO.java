package com.order_pay_service.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class InvoiceRequestDTO {
    private String customerCode;
    private String shippingAddress;
    private Double totalAmount;
    private List<InvoiceDetailDTO> details;
}
