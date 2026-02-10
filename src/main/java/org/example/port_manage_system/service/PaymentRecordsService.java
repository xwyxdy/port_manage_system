package org.example.port_manage_system.service;

import org.example.port_manage_system.domain.vo.PaymentRecordsVO;

public interface PaymentRecordsService {

    //根据支付id查询支付记录
    PaymentRecordsVO getPaymentRecordById(Integer id);

    //根据订单id删除支付记录
    boolean deletePaymentRecordByOrderId(Integer orderId);
}
