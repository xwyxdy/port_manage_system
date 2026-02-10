package org.example.port_manage_system.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.port_manage_system.domain.dto.PaymentOrdersDTO;
import org.example.port_manage_system.domain.vo.PaymentOrdersVO;

@Mapper
public interface PaymentOrdersMapper {

    //根据入港申请id查找支付订单
    @Select("select * from payment_orders where entry_id=#{id}")
    PaymentOrdersDTO getPaymentOrdersById(Integer id);

    //根据入港申请id删除支付订单
    @Delete("delete from payment_orders where entry_id= #{entryId}")
    boolean deleteByEntryId(Integer entryId);

}
