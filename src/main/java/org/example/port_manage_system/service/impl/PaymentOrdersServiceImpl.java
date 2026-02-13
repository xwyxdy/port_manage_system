package org.example.port_manage_system.service.impl;

import org.example.port_manage_system.domain.dto.InPortDTO;
import org.example.port_manage_system.domain.dto.OrdersPageDTO;
import org.example.port_manage_system.domain.dto.OrdersResponseDTO;
import org.example.port_manage_system.domain.dto.PaymentOrdersDTO;
import org.example.port_manage_system.domain.entity.PaymentOrders;
import org.example.port_manage_system.domain.entity.PaymentRecords;
import org.example.port_manage_system.domain.entity.Ship;
import org.example.port_manage_system.exception.BusinessException;
import org.example.port_manage_system.mapper.PaymentOrdersMapper;
import org.example.port_manage_system.mapper.PaymentRecordsMapper;
import org.example.port_manage_system.service.PaymentOrdersService;
import org.example.port_manage_system.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PaymentOrdersServiceImpl implements PaymentOrdersService {

    @Autowired
    private PaymentOrdersMapper paymentOrdersMapper;
    @Autowired
    private InPortServiceImpl inPortService;
    @Autowired
    private ShipService shipService;
    @Autowired
    private PaymentRecordsMapper paymentRecordsMapper;

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

    @Override
    public OrdersPageDTO getByPaymentStatus(String paymentStatus) {
        List<PaymentOrders> orders = paymentOrdersMapper.getByPaymentStatus(paymentStatus);
        List<OrdersResponseDTO> responseList=new ArrayList<>();
        for(PaymentOrders order:orders){
            Integer entryId = order.getEntryId();
            InPortDTO inPortDTO = inPortService.getById(entryId);
            Integer shipId = inPortDTO.getShipId();
            Ship ship=shipService.getById(shipId);
            BigDecimal amounts = paymentRecordsMapper.sumPaidAmountByOrderId(order.getId());
            OrdersResponseDTO response=new OrdersResponseDTO();
            response.setPaidAmount(amounts);
            response.setId(order.getId());
            response.setEntryId(entryId);
            response.setShipName(ship.getShipName());
            response.setFeeUnitPrice(order.getFeeUnitPrice());
            response.setTotalFee(order.getTotalFee());
            response.setPaymentStatus(order.getPaymentStatus());
            responseList.add(response);
        }
        return new OrdersPageDTO(responseList,responseList.size());
    }

    @Override
    public boolean updatePaymentStatus(Integer id) {
            return paymentOrdersMapper.updatePaymentStatus(id);
    }


    @Override
    public PaymentOrders getById(Integer id) {
        return paymentOrdersMapper.getById(id);
    }
}
