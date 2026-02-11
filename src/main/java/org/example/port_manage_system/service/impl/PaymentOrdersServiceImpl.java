package org.example.port_manage_system.service.impl;

import org.example.port_manage_system.domain.dto.InPortDTO;
import org.example.port_manage_system.domain.dto.PaymentOrdersDTO;
import org.example.port_manage_system.domain.entity.Ship;
import org.example.port_manage_system.exception.BusinessException;
import org.example.port_manage_system.mapper.PaymentOrdersMapper;
import org.example.port_manage_system.service.PaymentOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaymentOrdersServiceImpl implements PaymentOrdersService {

    @Autowired
    private PaymentOrdersMapper paymentOrdersMapper;
    @Autowired
    private InPortServiceImpl inPortService;

    @Override
    public PaymentOrdersDTO getOrdersByEntryId(Integer id) {
        try {
            InPortDTO inPortDTO= inPortService.getById(id);
            if(inPortDTO==null){
                throw new BusinessException("船只未提交入港申请");
            }
            return paymentOrdersMapper.getPaymentOrdersById(id);
        }catch (BusinessException e){
            throw e;
        }catch (Exception e) {
            throw new BusinessException("查询异常"+e.getMessage());
        }
    }

    @Override
    public boolean deleteOrdersByEntryId(Integer id) {
        try {
            boolean result = paymentOrdersMapper.deleteByEntryId(id);
            if(result){
                System.out.println("支付订单删除成功");
                return true;
            }else{
                throw new BusinessException("支付订单删除失败");
            }
        } catch (Exception e) {
            throw new BusinessException("支付订单删除异常"+e.getMessage());
        }
    }
}
