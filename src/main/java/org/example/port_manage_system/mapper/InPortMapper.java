package org.example.port_manage_system.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.port_manage_system.domain.dto.InPortDTO;
import org.example.port_manage_system.domain.entity.Ship;

@Mapper
public interface InPortMapper {

    //在入港申请表中删除船只信息
    @Delete("delete from port_entry_applications where ship_id = #{shipId}")
    boolean deleteByShipId(Integer shipId);

    //在入港申请表中根据id查询船只
    @Select("select * from port_entry_applications where ship_id=#{shipId}")
    InPortDTO getByShipId (Integer shipId);

    //在入港申请表中，根据主键id查询船只
    @Select("select * from port_entry_applications where id= #{id}")
    InPortDTO getById (Integer id);
}
