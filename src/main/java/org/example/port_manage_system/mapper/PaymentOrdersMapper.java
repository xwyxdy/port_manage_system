package org.example.port_manage_system.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.port_manage_system.domain.dto.PaymentOrdersDTO;
import org.example.port_manage_system.domain.entity.PaymentOrders;
import org.example.port_manage_system.domain.vo.PaymentOrdersVO;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface PaymentOrdersMapper {

    //根据入港申请id查找支付订单
    @Select("select * from payment_orders where entry_id=#{id}")
    PaymentOrdersDTO getPaymentOrdersById(Integer id);

    //根据入港申请id删除支付订单
    @Delete("delete from payment_orders where entry_id= #{entryId}")
    boolean deleteByEntryId(Integer entryId);

    /**
     * 根据支付状态获取订单
     */
    @Select("select * from payment_orders where payment_status=#{paymentStatus}")
    List<PaymentOrders> getByPaymentStatus(String paymentStatus);

    /**
     * 若支付记录>=totalFee则更改支付状态为PAID
     */
    @Update("update payment_orders set payment_status='PAID' where id=#{id}")
    boolean updatePaymentStatus(Integer id);

    /**
     * 根据订单id查询订单
     */
    @Select("select * from payment_orders where id= #{id}")
    PaymentOrders getById(Integer id);

}
