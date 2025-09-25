package com.productRest.service;

import com.productRest.dtos.ProductDto;
import com.productRest.entities.Product;
import com.productRest.exception.ProductNotFoundException;
import com.productRest.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<ProductDto> getAll() {
        var products = productRepository.findAll();
        return products
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
    }

    @Override
    public ProductDto getById(int id) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product with "+id+" not found"));

        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        Product saved = productRepository.save(product);
        return modelMapper.map(saved, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(int id, ProductDto productDto) {
        if(!productRepository.existsById(id)){
            throw new ProductNotFoundException("Product with ID "+id+" not found");
        }
        Product product = modelMapper.map(productDto, Product.class);
        product.setId(id);
        Product saved = productRepository.save(product);
        return modelMapper.map(saved, ProductDto.class);
    }

    @Override
    public String deleteProduct(int id) {
        if(!productRepository.existsById(id)){
            throw new ProductNotFoundException("Product with ID "+id+" not found");
        }
        productRepository.deleteById(id);
        return "Product with ID "+id+" deleted successfully";
    }
}
