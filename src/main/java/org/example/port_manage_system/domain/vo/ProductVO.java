package org.example.port_manage_system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVO {

    private Integer id;
    private String product_name;
    private Integer quantity;
    private Integer category_id;
    private BigDecimal price;
    private String status;
    private Integer created_by;
    private String category_name;
}
