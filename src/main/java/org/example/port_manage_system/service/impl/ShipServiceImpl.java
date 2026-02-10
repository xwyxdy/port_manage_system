package org.example.port_manage_system.service.impl;

import org.example.port_manage_system.domain.bo.InPortBO;
import org.example.port_manage_system.domain.bo.ShipBO;
import org.example.port_manage_system.domain.dto.InPortDTO;
import org.example.port_manage_system.domain.dto.ShipDTO;
import org.example.port_manage_system.domain.entity.Manager;
import org.example.port_manage_system.domain.entity.Ship;
import org.example.port_manage_system.domain.vo.InPortApplicationVO;
import org.example.port_manage_system.domain.vo.ShipVO;
import org.example.port_manage_system.mapper.ManagerMapper;
import org.example.port_manage_system.mapper.ShipMapper;
import org.example.port_manage_system.service.ShipService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ShipServiceImpl implements ShipService {

    @Autowired
    private ShipMapper shipMapper;
    @Autowired
    private ManagerMapper managerMapper;
    @Autowired
    private InPortServiceImpl inPortService;

    @Override
    public boolean addShip(ShipDTO shipDTO) {
        try {
            if(isShipNameExist(shipDTO.getShipName())){
                System.out.println("船只"+shipDTO.getShipName()+"已存在");
                return false;
            }
            //DTO->entity
            Ship ship=new Ship();
            BeanUtils.copyProperties(shipDTO,ship);
            //使用BO业务进行验证
            ShipBO shipBO=new ShipBO(ship,managerMapper);
            if(!shipBO.isValidShipType()){
                System.out.println("船只类型"+ship.getShipType()+"无效");
                return false;
            }
            if(!shipBO.isValidShipSize()){
                System.out.println("船只尺寸"+ship.getShipSize()+"无效");
                return false;
            }
            if(!shipBO.isValidQualificationStatus()){
                System.out.println("船只资格状态"+ship.getQualificationStatus()+"无效");
                return false;
            }
            if(!shipBO.isValidOwnerId()){
                System.out.println("船只"+ship.getShipName()+"船长id无效");
                return false;
            }
            int result=shipMapper.insert(ship);
            if(result>0){
                System.out.println("船只"+shipDTO.getShipName()+"添加成功");
                return true;
            }else{
                System.out.println("船只"+shipDTO.getShipName()+"添加失败");
                return false;
            }
        } catch (BeansException e) {
            System.out.println("添加船只异常：" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        try {
            Ship ship=shipMapper.getById( id);
            String shipName=ship.getShipName();
            if(ship==null){
                System.out.println("船只"+shipName+"不存在");
            }
            Ship shipInPort=inPortService.getByShipId(id);
            //如果船只未申请入港，直接删除
            if(shipInPort==null){
                int result=shipMapper.deleteById(ship.getId());
                if(result>0){
                    System.out.println("船只"+shipName+"删除成功");
                    return true;
                }else{
                    System.out.println("船只"+shipName+"删除失败");
                    return false;
                }
            }
            //先在入港申请表中删除船只信息
            boolean r=inPortService.deleteByShipId(ship.getId());
            if(r){
                int result=shipMapper.deleteById(ship.getId());
                if(result>0){
                    System.out.println("船只"+shipName+"删除成功");
                    return true;
                }else{
                    System.out.println("船只"+shipName+"删除失败");
                    return false;
                }
            }else{
                System.out.println("船只"+shipName+"在入港申请表中删除失败");
                return false;
            }
        } catch (Exception e) {
            System.out.println("删除船只异常：" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteByName(String name) {
        try {
            Ship ship=shipMapper.getByName(name);
            if(ship==null){
                System.out.println("船只"+name+"不存在");
            }
            Ship shipInPort=inPortService.getByShipId(ship.getId());
            //如果船只未申请入港，直接删除
            if(shipInPort==null){
                int result=shipMapper.deleteByName(name);
                if(result>0){
                    System.out.println("船只"+name+"删除成功");
                    return true;
                }else{
                    System.out.println("船只"+name+"删除失败");
                    return false;
                }
            }
            //先在入港申请表中删除船只信息
            boolean r=inPortService.deleteByShipId(ship.getId());
            if(r){
                int result=shipMapper.deleteByName(name);
                if(result>0){
                    System.out.println("船只"+name+"删除成功");
                    return true;
                }else{
                    System.out.println("船只"+name+"删除失败");
                    return false;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("删除船只异常"+e.getMessage());
            return false;
        }
    }

    @Override
    public Ship getById(Integer id) {
        Ship ship=shipMapper.getById(id);
        return ship;
    }

    @Override
    public Ship getByName(String name) {
        Ship ship=shipMapper.getByName(name);
        return ship;
    }

    @Override
    public List<ShipVO> getByType(String type) {
        if(!(type.equals("CARGO")||type.equals("FISHING")||type.equals("CRUISE"))){
            return null;
        }
        List<ShipVO> ships=shipMapper.getByType(type);
        return ships;
    }

    @Override
    public boolean login(String shipName, String userName, String password) {
        Ship ship=shipMapper.getByName(shipName);
        Integer ownerId=ship.getOwnerId();
        Manager manager=managerMapper.getById(ownerId);
        if(!manager.getUsername().equals(userName)){
            System.out.println("该船只不由"+userName+"管理");
            return false;
        }
        if(!manager.getPassword().equals(password)){
            System.out.println("密码错误");
            return false;
        }
        System.out.println("登录成功");
        return true;
    }

    @Override
    public boolean isShipNameExist(String shipName) {
        try {
            Ship ship=shipMapper.getByName(shipName);
            System.out.println("船只"+shipName+"是否存在："+(ship!=null));
            return ship!=null;
        } catch (Exception e) {
            System.out.println("判断船只名是否存在异常：" + e.getMessage());
            return false;
        }
    }
}
