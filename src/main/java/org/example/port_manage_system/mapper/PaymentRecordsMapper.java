package org.example.port_manage_system.mapper;

import org.apache.ibatis.annotations.*;
import org.example.port_manage_system.domain.dto.PaymentRecordsDTO;
import org.example.port_manage_system.domain.dto.RecordsQueryDTO;
import org.example.port_manage_system.domain.entity.PaymentRecords;
import org.example.port_manage_system.domain.vo.PaymentRecordsVO;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface PaymentRecordsMapper {
    //根据订单id删除支付记录
    @Delete("delete from payment_records where order_id=#{orderId}")
    boolean deletePaymentRecordByOrderId(Integer orderId);
    //根据支付id获取支付记录
    @Select("select * from payment_records where id= #{id}")
    PaymentRecordsVO getPaymentRecordById(Integer id);
    /**
     * 根据订单id获取支付记录
     */
    @Select("select * from payment_records where order_id= #{orderId}")
    List<PaymentRecords> getRecordsByOrderId(Integer orderId);

    /**
     * 插入一条支付记录
     */
    @Insert("insert into payment_records(order_id, payment_method, payment_amount) " +
            "values(#{orderId}, #{paymentMethod}, #{paymentAmount})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    boolean insertPaymentRecord(RecordsQueryDTO query);

    /**
     * 根据订单id获取支付id
     */
    @Select("select id from payment_records where order_id= #{orderId}")
    Integer getPaymentRecordIdByOrderId(Integer orderId);
    /**
     * 根据订单id统计已支付总金额
     */
    @Select("select IFNULL(sum(payment_amount),0) from payment_records where order_id=#{orderId}")
    BigDecimal sumPaidAmountByOrderId(Integer orderId);

}
