package com.order_pay_service.repository;

import com.order_pay_service.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    boolean existsBySeriesNumber(String seriesNumber);
}