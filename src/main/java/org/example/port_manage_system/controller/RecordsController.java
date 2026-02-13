package org.example.port_manage_system.controller;

import org.example.port_manage_system.domain.dto.RecordsQueryDTO;
import org.example.port_manage_system.domain.dto.RecordsResponseDTO;
import org.example.port_manage_system.domain.entity.PaymentOrders;
import org.example.port_manage_system.domain.vo.ResultVO;
import org.example.port_manage_system.service.PaymentOrdersService;
import org.example.port_manage_system.service.PaymentRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class RecordsController {
    @Autowired
    private PaymentRecordsService paymentRecordsService;
    @Autowired
    private PaymentOrdersService paymentOrdersService;

    @PostMapping("/api/payment-orders/{id}/pay")
    public ResultVO<RecordsResponseDTO> pay(@PathVariable Integer id,
                                            @RequestBody RecordsQueryDTO query) {
        try {
            // 1. 参数校验
            if (query.getPaymentAmount() == null ||
                    query.getPaymentAmount().compareTo(BigDecimal.ZERO) <= 0) {
                return ResultVO.error("支付金额必须大于0");
            }

            // 2. 查询订单
            PaymentOrders order = paymentOrdersService.getById(id);
            if (order == null) {
                return ResultVO.error("订单不存在");
            }

            BigDecimal totalFee = order.getTotalFee();

            // 3. 查询历史已付金额
            BigDecimal alreadyPaid = paymentRecordsService.getTotalPaidAmount(id);

            // 4. 计算支付后总金额
            BigDecimal newPaid = alreadyPaid.add(query.getPaymentAmount());

            // 5. 超额支付校验
            if (newPaid.compareTo(totalFee) > 0) {
                return ResultVO.error("支付金额超过待支付金额！");
            }
            query.setOrderId(id);

            // 6. 插入支付记录
            boolean saved = paymentRecordsService.insertPaymentRecords(query);
            if (!saved) {
                return ResultVO.error("支付记录保存失败");
            }

            // 7. 判断是否付清
            String status;
            if (newPaid.compareTo(totalFee) >= 0) {
                paymentOrdersService.updatePaymentStatus(id);
                status = "PAID";
            } else {
                status = "UNPAID";
            }

            // 8. 返回结果
            RecordsResponseDTO response = new RecordsResponseDTO();
            response.setOrderId(id);
            response.setPaidAmount(newPaid); // 返回累计已付金额
            response.setPaymentStatus(status);
            response.setPaymentRecordId(
                    paymentRecordsService.getRecordIdByOrderId(id)
            );

            return ResultVO.success("支付成功", response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.error("支付异常：" + e.getMessage());
        }
    }

}
