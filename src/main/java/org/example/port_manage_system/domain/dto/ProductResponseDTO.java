package org.example.port_manage_system.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    private Integer id;
    private String productName;
    private Integer quantity;
    private Integer categoryId;
    private String categoryName;
    private BigDecimal unitPrice;
    private String status;
}
enum status{
    AVAILABLE,    // 在库
    OUT_OF_STOCK,     // 缺货
}
