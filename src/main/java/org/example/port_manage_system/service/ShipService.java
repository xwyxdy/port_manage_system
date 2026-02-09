package org.example.port_manage_system.service;

import org.example.port_manage_system.domain.dto.InPortDTO;
import org.example.port_manage_system.domain.dto.ShipDTO;
import org.example.port_manage_system.domain.entity.Ship;
import org.example.port_manage_system.domain.vo.InPortApplicationVO;
import org.example.port_manage_system.domain.vo.ShipVO;

import java.util.List;

public interface ShipService {

    //新增船只
    boolean addShip(ShipDTO shipDTO);

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

}
