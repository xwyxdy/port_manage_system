package org.example.port_manage_system.controller;

import org.example.port_manage_system.domain.dto.OrdersPageDTO;
import org.example.port_manage_system.domain.dto.OrdersQueryDTO;
import org.example.port_manage_system.domain.vo.ResultVO;
import org.example.port_manage_system.service.impl.PaymentOrdersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment-orders")
public class OrdersController {

    @Autowired
    private PaymentOrdersServiceImpl paymentOrdersService;

    /**
     * 根据支付状态分页查询
     * @return
     */
    @GetMapping
    public ResultVO<OrdersPageDTO> getByPaymentStatus (@RequestParam (defaultValue = "1") Integer page,
                                          @RequestParam (defaultValue = "10")Integer pageSize,
                                          @RequestParam (required = false) String paymentStatus){
        try {
            OrdersQueryDTO query = new OrdersQueryDTO();
            query.setPage(page);
            query.setPageSize(pageSize);
            query.setPaymentStatus(paymentStatus);
            OrdersPageDTO orders = paymentOrdersService.getByPaymentStatus(paymentStatus);
            return ResultVO.success("查询成功",orders);
        } catch (Exception e) {
            return ResultVO.error("查询异常"+e.getMessage());
        }
    }
}
