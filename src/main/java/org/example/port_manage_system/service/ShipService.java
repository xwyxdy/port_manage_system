package org.example.port_manage_system.service;

import org.example.port_manage_system.domain.dto.ShipPageDTO;
import org.example.port_manage_system.domain.dto.ShipQueryDTO;
import org.example.port_manage_system.domain.dto.ShipRequestDTO;
import org.example.port_manage_system.domain.dto.ShipResponseDTO;
import org.example.port_manage_system.domain.entity.Ship;
import org.example.port_manage_system.domain.vo.ProductVO;
import org.example.port_manage_system.domain.vo.ShipCargoVO;
import org.example.port_manage_system.domain.vo.ShipVO;

import java.util.List;

public interface ShipService {

    //新增船只
    ShipResponseDTO addShip(ShipRequestDTO request);

    //根据船只id删除船只
    boolean deleteById(Integer id);

    //根据船名删除船只
    boolean deleteByName(String name);

    //根据船只id查询船只
    Ship getById(Integer id);

    //根据船名查询船只
    Ship getByName(String name);

    //根据船只类型查询船只
    List<ShipVO> getByType(String type);

    //船只的登录，输入船名，船长名和id
    boolean login(String shipName,String userName,String password);

    //检查船只名是否已经存在
    boolean isShipNameExist(String shipName);

    //船长向船上添加商品
    ShipCargoVO addProductToShip(Integer shipId,Integer productId,Integer cargoQuantity);

    //船长删除船上某商品
    boolean deleteProductFromShip(Integer id);

    //船长修改某商品数量
    boolean updateProductQuantity(Integer id,Integer cargoQuantity);

    //船长查询船上所有商品
    List<ProductVO> getAllProductsInShip(Integer shipId);

    //船长查询船上某商品
    ProductVO getProductInShip(Integer shipId,Integer productId);

    //船长查询船上所有有库存商品
    List<ProductVO> getAllAvailableProductsInShip(Integer shipId);


    /**
     * 分页查询船只
     * @param queryDTO
     * @return
     */
    ShipPageDTO getShipList(ShipQueryDTO queryDTO);
}
