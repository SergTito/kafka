package com.sergtito.ws.productmicroservice.service;

import com.sergtito.ws.productmicroservice.service.dto.CreateProductDTO;

public interface ProductService {

    String createProduct(CreateProductDTO dto);
}
