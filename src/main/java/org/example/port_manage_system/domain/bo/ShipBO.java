package org.example.port_manage_system.domain.bo;

import lombok.Data;
import org.example.port_manage_system.domain.entity.Manager;
import org.example.port_manage_system.domain.entity.Ship;
import org.example.port_manage_system.mapper.ManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class ShipBO {

    @Autowired
    private ManagerMapper managerMapper;
    private Ship ship;
    public ShipBO(Ship ship, ManagerMapper managerMapper){
        this.ship=ship;
        this.managerMapper=managerMapper;
    }
    //业务逻辑：检查船只类型
    public boolean isValidShipType(){
        return ship.getShipType().equals("CARGO")||
                ship.getShipType().equals("FISHING")||
                ship.getShipType().equals("CRUISE");
    }
    //业务逻辑：检查船只大小
    public boolean isValidShipSize(){
        return ship.getShipSize().equals("SMALL")||
                ship.getShipSize().equals("MEDIUM")||
                ship.getShipSize().equals("LARGE");
    }
    //业务逻辑：检查船只资格状态
    public boolean isValidQualificationStatus(){
        return ship.getQualificationStatus().equals("APPROVED")||
                ship.getQualificationStatus().equals("REJECTED")||
                ship.getQualificationStatus().equals("PENDING")||
                ship.getQualificationStatus()==null||
                ship.getQualificationStatus().isEmpty();
    }

    //业务逻辑：检查船只的ownerId在用户表中对应的用户类型是否是船长
    public boolean isValidOwnerId(){
        Integer id=ship.getOwnerId();
        Manager manager=managerMapper.getById(id);
        return manager.getUserType().equals("SHIP_OWNER");
    }
}
