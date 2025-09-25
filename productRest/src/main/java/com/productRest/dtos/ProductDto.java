package com.productRest.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {
    private int id;
    private String prodName;
    private String prodDesc;
    private double price;
    private int quantity;
    private LocalDate manufacturingDate;
}
