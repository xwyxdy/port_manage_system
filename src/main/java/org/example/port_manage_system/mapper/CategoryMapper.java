package org.example.port_manage_system.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CategoryMapper {

    //根据id查询类别
    @Select(("select category_name from product_categories where id=#{id}"))
    String getCategoryNameById(Integer id);

    //根据类别名称查询类别id
    @Select("select id from product_categories where category_name= #{categoryName}")
    Integer getCategoryIdByName(String categoryName);
}
