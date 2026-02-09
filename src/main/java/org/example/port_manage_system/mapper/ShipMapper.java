package org.example.port_manage_system.mapper;

import org.apache.ibatis.annotations.*;
import org.example.port_manage_system.domain.dto.InPortDTO;
import org.example.port_manage_system.domain.entity.Ship;
import org.example.port_manage_system.domain.vo.ShipVO;

import java.util.List;

@Mapper
public interface ShipMapper {
    //新增船只，并返回自增id
    @Insert("insert into ships(id,ship_name,owner_id,ship_type,ship_size,qualification_status)"+
    "values(#{id},#{shipName},#{ownerId},#{shipType},#{shipSize},null)")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insert(Ship ship);

    //根据船名删除船只
    @Delete("delete from ships where ship_name = #{shipName}")
    int deleteByName(String shipName);

    //根据船只id删除船只
    @Delete("delete from ships where id = #{id}")
    int deleteById(Integer id);

    //根据船只id查询船只
    @Select("select * from ships where id = #{id}")
    Ship getById(Integer id);

    //根据船只名查询船只
    @Select("select * from ships where ship_name = #{shipName}")
    Ship getByName(String shipName);

    //根据船只类型查询船只
    @Select("select * from ships where ship_type = #{shipType}")
    List<ShipVO> getByType(String shipType);

    //提交入港申请
    @Insert("insert into port_entry_applications(ship_id,entry_time,exit_time,application_status,review_status,reviewer_id)"+
    "values (#{shipId},NOW(),null,#{applicationStatus},'PENDING',null)")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int applyInPort(InPortDTO inPortDTO);

    //提交入港申请的同时，要把ships表中的状态改为PENDING
    @Update("update ships set qualification_status = 'PENDING' where id = #{id}")
    int updateQualificationStatus(Integer id);

    //根据船只id查询最新的入港申请
    @Select("SELECT * FROM port_entry_applications WHERE ship_id = #{shipId} ORDER BY id DESC LIMIT 1")
    InPortDTO getLatestApplicationByShipId(@Param("shipId") Integer shipId);
}
