package com.productRest.service;

import com.productRest.dtos.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAll();
    ProductDto getById(int id);
    ProductDto addProduct(ProductDto productDto);
    ProductDto updateProduct(int id, ProductDto productDto);
    String deleteProduct(int id);
}
