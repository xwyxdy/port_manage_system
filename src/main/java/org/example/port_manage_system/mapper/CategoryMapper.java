package org.example.port_manage_system.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CategoryMapper {

    //根据id查询类别
    String getCategoryByCategoryId(Integer id);

    //根据类别名称查询类别id
    Integer getCategoryIdByName(String categoryName);
}
