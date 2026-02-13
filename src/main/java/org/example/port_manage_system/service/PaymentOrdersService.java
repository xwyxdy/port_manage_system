package org.example.port_manage_system.service;

import org.example.port_manage_system.domain.dto.OrdersPageDTO;
import org.example.port_manage_system.domain.dto.PaymentOrdersDTO;
import org.example.port_manage_system.domain.entity.PaymentOrders;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentOrdersService {

    //根据入港申请id查询支付订单
    PaymentOrdersDTO getOrdersByEntryId(Integer id);

    //根据入港申请id删除支付订单
    boolean deleteOrdersByEntryId(Integer id);

    /**
     * 根据支付状态获取订单
     */
    OrdersPageDTO getByPaymentStatus(String paymentStatus);

    /**
     * 若金额已达要求则将支付状态改为PAID
     */
    boolean updatePaymentStatus(Integer id);

    /**
     * 根据id查询订单
     */
    PaymentOrders getById(Integer id);
}
