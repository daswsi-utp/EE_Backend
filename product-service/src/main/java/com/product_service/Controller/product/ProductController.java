package com.product_service.Controller.product;

import com.product_service.dto.product.request.ProductRequestDTO;
import com.product_service.dto.product.response.ProductResponseDTO;
import com.product_service.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @RequestPart("product") ProductRequestDTO productDTO,
            @RequestPart("image") MultipartFile imageFile
    ) {
        return ResponseEntity.ok(productService.createProduct(productDTO, imageFile));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{code}")
    public ResponseEntity<ProductResponseDTO> getProductByCode(@PathVariable Integer code) {
        return ResponseEntity.ok(productService.getProductByCode(code));
    }

    @PutMapping("/{code}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Integer code,
            @RequestPart("product") ProductRequestDTO updatedDTO,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) {
        return ResponseEntity.ok(productService.updateProduct(code, updatedDTO, imageFile));
    }


    @PatchMapping("/{code}/activate")
    public ResponseEntity<Void> activateProduct(@PathVariable Integer code) {
        productService.activateProduct(code);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{code}/deactivate")
    public ResponseEntity<Void> deactivateProduct(@PathVariable Integer code) {
        productService.deactivateProduct(code);
        return ResponseEntity.noContent().build();
    }
}
