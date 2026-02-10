package org.example.port_manage_system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOrdersVO {
    private Integer id;
    private Integer entryId;
    private BigDecimal feeUnitPrice;
    private BigDecimal totalFee;
    private  String paymentStatus;
}

