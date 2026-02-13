package org.example.port_manage_system.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersResponseDTO {
    private Integer id;
    private Integer entryId;
    private String shipName;
    private BigDecimal feeUnitPrice;
    private BigDecimal totalFee;
    private String paymentStatus;
    private BigDecimal paidAmount;
}
