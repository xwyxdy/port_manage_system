package org.example.port_manage_system.service;

import org.example.port_manage_system.domain.dto.RecordsQueryDTO;
import org.example.port_manage_system.domain.vo.PaymentRecordsVO;

import java.math.BigDecimal;

public interface PaymentRecordsService {

    //根据支付id查询支付记录
    PaymentRecordsVO getPaymentRecordById(Integer id);

    //根据订单id删除支付记录
    boolean deletePaymentRecordByOrderId(Integer orderId);

    /**
     * 插入一条支付记录
     */
    boolean insertPaymentRecords (RecordsQueryDTO  query);
    /**
     * 根据订单id获取支付记录
     */
    Integer getRecordIdByOrderId(Integer orderId);

    BigDecimal getTotalPaidAmount(Integer orderId);

}
