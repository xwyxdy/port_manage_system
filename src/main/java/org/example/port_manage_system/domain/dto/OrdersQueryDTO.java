package org.example.port_manage_system.domain.dto;

import lombok.Data;

@Data
public class OrdersQueryDTO {
    private Integer page=1;
    private Integer pageSize=10;
    private String paymentStatus;
}
