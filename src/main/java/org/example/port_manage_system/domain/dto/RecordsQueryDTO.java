package org.example.port_manage_system.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RecordsQueryDTO {
    private Integer orderId;
    private String paymentMethod;
    private BigDecimal paymentAmount;
}
