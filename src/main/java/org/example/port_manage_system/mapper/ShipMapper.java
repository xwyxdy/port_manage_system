package org.example.port_manage_system.mapper;

import org.apache.ibatis.annotations.*;
import org.example.port_manage_system.domain.dto.InPortDTO;
import org.example.port_manage_system.domain.dto.ShipQueryDTO;
import org.example.port_manage_system.domain.dto.ShipResponseDTO;
import org.example.port_manage_system.domain.entity.Ship;
import org.example.port_manage_system.domain.vo.ProductVO;
import org.example.port_manage_system.domain.vo.ShipCargoVO;
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

    //船长向船上添加商品
    @Insert("insert into ship_cargo(ship_id,product_id,cargo_quantity) values (#{shipId},#{productId},{cargoQuantity})")
    ShipCargoVO addProductToShip(Integer shipId,Integer productId,Integer cargoQuantity);

    //船长删除船上某商品
    @Delete("delete from ship_cargo where id=#{id}")
    boolean deleteProductFromShip(Integer id);

    //船长修改某商品数量
    @Update("update ship_cargo set cargo_quantity = #{cargoQuantity} where id = #{id}")
    boolean updateProductQuantity(Integer id,Integer cargoQuantity);

    //船长查询船上所有商品
    @Select("select * from ship_cargo where ship_id = #{shipId}")
    List<ProductVO> getAllProductsInShip(Integer shipId);

    //船长查询船上某商品
    @Select("select * from ship_cargo where ship_id = #{shipId} and product_id = #{productId}")
    ProductVO getProductInShip(Integer shipId,Integer productId);

    //船长查询船上所有有库存商品
    @Select("select * from ship_cargo where ship_id = #{shipId} and cargo_quantity > 0")
    List<ProductVO> getAllAvailableProductsInShip(Integer shipId);

    @Select({
            "<script>",
            "SELECT s.id, s.ship_name as shipName, s.owner_id as ownerId, ",
            "u.username as ownerName, ",
            "s.ship_type as shipType, s.ship_size as shipSize, ",
            "s.qualification_status as qualificationStatus ",
            "FROM ships s ",
            "LEFT JOIN users u ON s.owner_id = u.id ",
            "WHERE 1=1",
            "<if test='qualificationStatus != null and qualificationStatus != \"\"'> AND s.qualification_status = #{qualificationStatus} </if>",
            "LIMIT #{offset}, #{pageSize}",
            "</script>"
    })
    List<ShipResponseDTO> selectShipList(@Param("qualificationStatus") String qualificationStatus,
                                         @Param("offset") int offset,
                                         @Param("pageSize") int pageSize);

    @Select({
            "<script>",
            "SELECT COUNT(*) FROM ships s ",
            "WHERE 1=1",
            "<if test='qualificationStatus != null and qualificationStatus != \"\"'> AND s.qualification_status = #{qualificationStatus} </if>",
            "</script>"
    })
    int countShipList(@Param("qualificationStatus") String qualificationStatus);
}
