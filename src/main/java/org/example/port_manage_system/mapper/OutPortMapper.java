package org.example.port_manage_system.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OutPortMapper {

    @Update("UPDATE port_entry_applications " +
            "SET exit_time = NOW() " +
            "WHERE ship_id = #{shipId} " +
            "  AND exit_time IS NULL " +
            "  AND entry_time IS NOT NULL " +
            "  AND review_status = 'APPROVED' " +
            "  AND application_status = 'SUBMITTED' " +
            "ORDER BY entry_time DESC ")
    boolean updateExitTime(Integer shipId);
}
