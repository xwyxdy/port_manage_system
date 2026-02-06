package org.example.port_manage_system.mapper;

import org.apache.ibatis.annotations.*;
import org.example.port_manage_system.domain.entity.Product;

import java.util.List;

@Mapper
public interface ProductMapper {

    //查询所有商品
    @Select("select * from products")
    List<Product> getAll();

    //根据id查询商品
    @Select("select * from products where id = #{id}")
    Product getById(Integer id);

    //根据商品名称查询商品
    @Select("select * from products where product_name= #{productName}")
    Product getByName(String productName);

    //根据商品类别查询商品
    @Select("select * from products where category_id= #{categoryId}")
    List<Product> getByCategoryId(Integer categoryId);

    //根据商品状态查询商品
    @Select("select * from products where status = 'AVAILABLE'")
    List<Product> getAllAvailable();

    //插入商品返回自增id
    @Insert("insert into products(product_name,quantity,category_id,unit_price,status,created_by)"
    +"values(#{productName},#{quantity},#{categoryId},#{unit_price},#{status},#{createdBy})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insert(Product product);

    //根据id删除商品
    @Delete("delete from products where id = #{id}")
    int delete(Integer id);

    //更新商品信息
    @Update("update products set quantity= #{quantity},unit_price=#{unit_price} WHERE id=#{id}")
    int update(Product product);

    //检查商品是否被使用
    @Select("select count(*) from ship_cargo where product_id = #{id}")
    int checkProductUsage(Integer id);
}
