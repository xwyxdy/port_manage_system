package org.example.port_manage_system.domain.dto;

import lombok.Data;

@Data
public class ProductQueryDTO {
    private Integer page=1;
    private Integer pageSize=10;
    private Integer categoryId;
}
