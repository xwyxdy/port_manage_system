package org.example.port_manage_system.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Integer id;
    private String productName;
    private Integer quantity;
    private Integer categoryId;
    private BigDecimal unitPrice;
    private String status;
    private Integer createdBy;


}
enum Status{
    AVAILABLE,
    OUT_OF_STOCK
}
