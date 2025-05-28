package com.report_service.entities;

import java.time.LocalDate;

public class Sale {
    public LocalDate date;
    public Product product;
    public Integer quantity;
    public double total;

    public Sale(LocalDate date, Product product, Integer quantity, double total) {
        this.date = date;
        this.product = product;
        this.quantity = quantity;
        this.total = total;
    }

    public double getTotal() {
        return 0;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
