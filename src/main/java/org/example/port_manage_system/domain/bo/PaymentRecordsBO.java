package org.example.port_manage_system.domain.bo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.port_manage_system.domain.dto.PaymentRecordsDTO;

@Data
@NoArgsConstructor
public class PaymentRecordsBO {
    private PaymentRecordsDTO paymentRecordsDTO;
    public PaymentRecordsBO(PaymentRecordsDTO paymentRecordsDTO) {
        this.paymentRecordsDTO = paymentRecordsDTO;
    }
    //业务逻辑：检查支付方式是否规范
    public boolean isValidPaymentMethod(){
        return paymentRecordsDTO.getPaymentMethod().equals("ALIPAY")||
                paymentRecordsDTO.getPaymentMethod().equals("WECHAT_PAY")||
                paymentRecordsDTO.getPaymentMethod().equals("BANK TRANSFER");
    }
}
