package org.example.port_manage_system.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordsResponseDTO {
    private Integer orderId;
    private Integer paymentRecordId;
    private String paymentStatus;
    private BigDecimal paidAmount;
}
