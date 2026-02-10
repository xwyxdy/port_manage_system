package org.example.port_manage_system.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.port_manage_system.domain.dto.PaymentRecordsDTO;
import org.example.port_manage_system.domain.vo.PaymentRecordsVO;

@Mapper
public interface PaymentRecordsMapper {
    //根据订单id删除支付记录
    @Delete("delete from payment_records where order_id=#{orderId}")
    boolean deletePaymentRecordByOrderId(Integer orderId);
    //根据支付id获取支付记录
    @Select("select * from payment_records where id= #{id}")
    PaymentRecordsVO getPaymentRecordById(Integer id);
}
