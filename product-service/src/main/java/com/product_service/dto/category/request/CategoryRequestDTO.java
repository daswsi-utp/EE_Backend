package com.product_service.dto.category.request;

import lombok.Data;

@Data
public class CategoryRequestDTO {
    private String categoryId;
    private String name;
    private String description;
}
