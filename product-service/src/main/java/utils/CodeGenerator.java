package com.product_service.utils;

import com.product_service.repository.product.ProductRepository;

import java.util.Random;

public class CodeGenerator {

    public static int generateUniqueCode(ProductRepository productRepository) {
        Random random = new Random();
        int code;
        do {
            code = 10000 + random.nextInt(90000);
        } while (productRepository.existsByCode(code));
        return code;
    }
}
