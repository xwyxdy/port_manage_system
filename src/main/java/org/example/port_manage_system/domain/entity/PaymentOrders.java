package org.example.port_manage_system.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOrders {
    private Integer id;
    private Integer entryId;
    private BigDecimal feeUnitPrice;
    private BigDecimal totalFee;
    private  String paymentStatus;
}
enum paymentStatus{
    UNPAID,
    PAID,
    OVERDUE
}
