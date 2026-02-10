package org.example.port_manage_system.domain.bo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.port_manage_system.domain.dto.PaymentOrdersDTO;

@Data
@NoArgsConstructor
public class PaymentOrdersBO {
    private PaymentOrdersDTO paymentOrdersDTO;
    public PaymentOrdersBO(PaymentOrdersDTO paymentOrdersDTO) {
        this.paymentOrdersDTO = paymentOrdersDTO;
    }

    //业务逻辑：检查支付订单状态
    public boolean isValidPaymentStatus(){
        return paymentOrdersDTO.getPaymentStatus().equals("PAID")||
                paymentOrdersDTO.getPaymentStatus().equals("UNPAID");
    }
    //业务逻辑：检查订单是否已支付
    public boolean isPaid(String paymentStatus){
        return paymentStatus.equals("PAID");
    }
}
