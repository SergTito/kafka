package com.sergtito.ws.productmicroservice.controller;

import com.sergtito.ws.productmicroservice.service.ProductService;
import com.sergtito.ws.productmicroservice.service.dto.CreateProductDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody CreateProductDTO createProductDTO){
        String productID = productService.createProduct(createProductDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(productID);
    }
}
