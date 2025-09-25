package com.productRest.service;

import com.productRest.dtos.ProductDto;
import com.productRest.entities.Product;
import com.productRest.exception.ProductNotFoundException;
import com.productRest.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    ProductServiceImpl productService;

    ProductDto prod1;
    ProductDto prod2;
    Product product1;
    Product product2;
    List<ProductDto> products;

    @BeforeEach
    void init(){
        prod1 = new ProductDto(1, "HP", "Best", 233442233, 3, LocalDate.now());
        prod2 = new ProductDto(2, "Mouse", "Best", 233442233, 1, LocalDate.now());
        product1 = new Product(1, "HP", "Best", 233442233, 3, LocalDate.now());
        product2 = new Product(2, "Mouse", "Best", 233442233, 1, LocalDate.now());
        products = List.of(prod1, prod2);
    }

    @Test
    void getAll() {
        when(productRepository.findAll()).thenReturn(List.of(product1, product2));
        when(modelMapper.map(any(Product.class), eq(ProductDto.class))).thenReturn(prod1);

        var result  = productService.getAll();

        assertEquals(2, result.size());
        assertEquals("HP", result.get(0).getProdName());
    }


    @Test
    void getById() {
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product1));
        when(modelMapper.map(any(Product.class), eq(ProductDto.class))).thenReturn(prod1);

        var result = productService.getById(1);

        assertNotNull(result);
        assertEquals("HP", result.getProdName());

        verify(productRepository).findById(1);
        verify(modelMapper).map(any(Product.class), eq(ProductDto.class));
    }

    @Test
    void addProduct() {
        when(productRepository.save(product1)).thenReturn(product1);
        when(modelMapper.map(any(Product.class), eq(ProductDto.class))).thenReturn(prod1);
        when(modelMapper.map(any(ProductDto.class), eq(Product.class))).thenReturn(product1);

        ProductDto prod = productService.addProduct(prod1);

        assertEquals("HP", prod.getProdName());
        assertNotNull(prod);
        verify(productRepository).save(product1);
    }

    @Test
    void updateProduct() {
        when(productRepository.existsById(2)).thenReturn(true);
        when(modelMapper.map(any(ProductDto.class), eq(Product.class))).thenReturn(product2);
        when(productRepository.save(product2)).thenReturn(product2);
        when(modelMapper.map(any(Product.class), eq(ProductDto.class))).thenReturn(prod2);


        ProductDto prod = productService.updateProduct(2, prod2);

        assertEquals("Mouse", prod.getProdName());
        assertNotNull(prod);
        verify(productRepository).save(product2);
    }

    @Test
    void updateProductNotFound(){
        when(productRepository.existsById(100)).thenReturn(false);
        assertThrows(ProductNotFoundException.class, ()-> productService.updateProduct(100, prod1));
        verify(productRepository).existsById(100);
    }

    @Test
    void deleteProduct() {
        when(productRepository.existsById(anyInt())).thenReturn(true);
        doNothing().when(productRepository).deleteById(anyInt());

        var result = productService.deleteProduct(1);

        assertNotNull(result);
        assertEquals("Product with ID "+1+" deleted successfully", result);
        verify(productRepository).existsById(1);
        verify(productRepository).deleteById(1);
    }

    @Test
    void deleteProductNotFound(){
        when(productRepository.existsById(anyInt())).thenReturn(false);
        doNothing().when(productRepository).deleteById(anyInt());

        assertThrows(ProductNotFoundException.class,
                () -> productService.deleteProduct(100));
        verify(productRepository).existsById(100);
        verify(productRepository, never()).deleteById(100);

    }

}