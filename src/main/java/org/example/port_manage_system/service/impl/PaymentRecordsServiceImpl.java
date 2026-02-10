package org.example.port_manage_system.service.impl;

import org.example.port_manage_system.domain.dto.PaymentRecordsDTO;
import org.example.port_manage_system.domain.vo.PaymentRecordsVO;
import org.example.port_manage_system.mapper.PaymentRecordsMapper;
import org.example.port_manage_system.service.PaymentRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaymentRecordsServiceImpl implements PaymentRecordsService {

    @Autowired
    private PaymentRecordsMapper paymentRecordsMapper;
    @Override
    public PaymentRecordsVO getPaymentRecordById(Integer id) {
        try {
            PaymentRecordsVO paymentRecordsVO= paymentRecordsMapper.getPaymentRecordById(id);
            if(paymentRecordsVO==null){
                System.out.println("支付记录不存在");
                return null;
            }
            return paymentRecordsVO;
        } catch (Exception e) {
            System.out.println("查询支付记录异常"+e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deletePaymentRecordByOrderId(Integer orderId) {
        try {
            boolean result = paymentRecordsMapper.deletePaymentRecordByOrderId(orderId);
            if(result){
                System.out.println("支付记录删除成功");
                return true;
            }else{
                System.out.println("支付记录删除失败");
                return false;
            }
        } catch (Exception e) {
            System.out.println("支付记录删除异常"+e.getMessage());
            return false;
        }
    }
}
