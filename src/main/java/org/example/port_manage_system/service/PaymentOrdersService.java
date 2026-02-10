package org.example.port_manage_system.service;

import org.example.port_manage_system.domain.dto.PaymentOrdersDTO;

public interface PaymentOrdersService {

    //根据入港申请id查询支付订单
    PaymentOrdersDTO getOrdersByEntryId(Integer id);

    //根据入港申请id删除支付订单
    boolean deleteOrdersByEntryId(Integer id);
}
