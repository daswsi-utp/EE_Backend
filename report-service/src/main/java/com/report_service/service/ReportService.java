package com.report_service.service;

import com.report_service.entities.Product;
import com.report_service.entities.Sale;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportService {

    public List<Sale> getSales(){
        List<Sale> sales = new ArrayList<>();

        Sale sale1 = new Sale(LocalDate.now(), new Product("Product1", 50), 2, 100.0);
        Sale sale2 = new Sale(LocalDate.now(), new Product("Product2", 50), 1, 50.0);

        sales.add(sale1);
        sales.add(sale2);
        return sales;
    }

}
