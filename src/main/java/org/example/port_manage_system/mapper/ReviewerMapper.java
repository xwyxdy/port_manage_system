package org.example.port_manage_system.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.port_manage_system.domain.entity.CargoShip;
import org.example.port_manage_system.domain.entity.Product;
import org.example.port_manage_system.domain.vo.InPortApplicationVO;
import org.example.port_manage_system.domain.vo.ProductInCargoVO;
import org.example.port_manage_system.domain.vo.ProductVO;

import java.util.List;

@Mapper
public interface ReviewerMapper {

    //审核员手动提交审核意见，根据船只id查找要修改的信息
    @Update("update port_entry_applications set review_status=#{reviewStatus} where ship_id=#{shipId}")
    boolean updateReviewStatus(String reviewStatus,Integer shipId);

    //审核员手动添加审核员id，根据船只id查找要修改的信息
    @Update("update port_entry_applications set reviewer_id=#{reviewerId} where ship_id=#{shipId}")
    boolean updateReviewerId(Integer reviewerId,Integer shipId);

    //审核员在审核表中通过审核类型查找船只
    @Select("select ship_id,review_status,reviewer_id from port_entry_applications where review_status=#{reviewStatus}")
    List<InPortApplicationVO> getByReview(String reviewStatus);

    //审核员可删除审核表中信息
    @Delete("delete from port_entry_applications where id=#{id}")
    boolean deleteById(Integer id);

    //审核员查找船上所有货物
    @Select("select * from ship_cargo where ship_id=#{shipId}")
    List<CargoShip> getAllGoodsInCargo(Integer shipId);
}
