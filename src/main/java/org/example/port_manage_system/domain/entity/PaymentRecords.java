package org.example.port_manage_system.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRecords {

    private Integer id;
    private Integer orderId;
    private String paymentMethod;
    private BigDecimal paymentAmount;
}
enum PaymentMethod{
    ALIPAY,
    WECHAT_PAY,
    BANK_TRANSFER
}
