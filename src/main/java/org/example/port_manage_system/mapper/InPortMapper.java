package org.example.port_manage_system.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InPortMapper {

    @Delete("delete from port_entry_applications where ship_id = #{shipId}")
    boolean deleteByShipId(Integer shipId);
}
