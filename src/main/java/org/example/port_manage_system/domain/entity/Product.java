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
    private String product_name;
    private Integer quantity;
    private Integer category_id;
    private BigDecimal price;
    private String status;
    private Integer created_by;


}
enum Status{
    AVAILABLE,
    OUT_OF_STOCK
}
