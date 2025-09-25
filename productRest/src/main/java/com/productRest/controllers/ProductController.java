package com.productRest.controllers;

import com.productRest.dtos.ProductDto;
import com.productRest.dtos.ResponseDto;
import com.productRest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/greet/hello")
    public String sayHello(){
        return "Hello from product service";
    }
    @GetMapping("/greet/welcome")
    public String sayWelcome(){
        return "Welcome from product service";
    }

    @GetMapping
    public List<ProductDto> displayAll(){
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable int id){
        return ResponseEntity.ok(productService.getById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto){
        ProductDto productDto1 = productService.addProduct(productDto);
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable int id, @RequestBody ProductDto productDto){
        return new ResponseEntity<>(productService.updateProduct(id, productDto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteProduct(@PathVariable int id){
        var result = productService.deleteProduct(id);
        ResponseDto dto = new ResponseDto();
        dto.setMessage(result);
        return ResponseEntity.ok(dto);
    }
}
