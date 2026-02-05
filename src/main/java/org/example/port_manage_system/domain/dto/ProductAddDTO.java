package org.example.port_manage_system.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAddDTO {
    private String productName;
    private Integer quantity;
    private Integer categoryId;
    private BigDecimal price;
    private String status;
    private Integer createdBy;
}

enum Status{
    AVAILABLE,  //可用
    OUT_OF_STOCK    //售罄
}
