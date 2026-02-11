package org.example.port_manage_system.service.impl;

import org.example.port_manage_system.domain.bo.InPortBO;
import org.example.port_manage_system.domain.bo.ShipBO;
import org.example.port_manage_system.domain.dto.InPortDTO;
import org.example.port_manage_system.domain.dto.ShipDTO;
import org.example.port_manage_system.domain.entity.Manager;
import org.example.port_manage_system.domain.entity.Ship;
import org.example.port_manage_system.domain.vo.InPortApplicationVO;
import org.example.port_manage_system.domain.vo.ProductVO;
import org.example.port_manage_system.domain.vo.ShipCargoVO;
import org.example.port_manage_system.domain.vo.ShipVO;
import org.example.port_manage_system.exception.BusinessException;
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
               throw new BusinessException("船只"+shipDTO.getShipName()+"已存在");
            }
            //DTO->entity
            Ship ship=new Ship();
            BeanUtils.copyProperties(shipDTO,ship);
            //使用BO业务进行验证
            ShipBO shipBO=new ShipBO(ship,managerMapper);
            if(!shipBO.isValidShipType()){
                throw new BusinessException("船只"+ship.getShipName()+"船只类型无效");
            }
            if(!shipBO.isValidShipSize()){
                throw new BusinessException("船只"+ship.getShipName()+"船只尺寸无效");
            }
            if(!shipBO.isValidQualificationStatus()){
                throw new BusinessException("船只"+ship.getShipName()+"船只资格状态无效");
            }
            if(!shipBO.isValidOwnerId()){
                throw new BusinessException("船只"+ship.getShipName()+"船长id无效");
            }
            int result=shipMapper.insert(ship);
            if(result>0){
                System.out.println("船只"+shipDTO.getShipName()+"添加成功");
                return true;
            }else{
                throw new BusinessException("船只"+ship.getShipName()+"添加失败");
            }
        }catch (BusinessException e){
            throw e;
        }catch (Exception e) {
            throw new BusinessException("船只添加异常：" + e.getMessage());
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        try {
            Ship ship=shipMapper.getById( id);
            String shipName=ship.getShipName();
            if(ship==null){
                throw new BusinessException("船只"+shipName+"不存在");
            }
            InPortDTO shipInPort=inPortService.getByShipId(id);
            //如果船只未申请入港，直接删除
            if(shipInPort==null){
                int result=shipMapper.deleteById(ship.getId());
                if(result>0){
                    System.out.println("船只"+shipName+"删除成功");
                    return true;
                }else{
                    throw new BusinessException("船只"+shipName+"删除失败");
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
                    throw new BusinessException("船只"+shipName+"删除失败");
                }
            }else{
                throw new BusinessException("船只"+shipName+"在入港申请表中删除失败");
            }
        }catch (BusinessException e){
            throw e;
        }catch (Exception e) {
            throw new BusinessException("船只删除异常：" + e.getMessage());
        }
    }

    @Override
    public boolean deleteByName(String name) {
        try {
            Ship ship=shipMapper.getByName(name);
            if(ship==null){
                throw new BusinessException("船只"+name+"不存在");
            }
            InPortDTO shipInPort=inPortService.getByShipId(ship.getId());
            //如果船只未申请入港，直接删除
            if(shipInPort==null){
                int result=shipMapper.deleteByName(name);
                if(result>0){
                    System.out.println("船只"+name+"删除成功");
                    return true;
                }else{
                    throw new BusinessException("船只"+name+"删除失败");
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
                    throw new BusinessException("船只"+name+"在入港申请表中删除失败");
                }
            }
            return false;
        }catch (BusinessException e){
            throw e;
        }catch (Exception e) {
            throw new BusinessException("船只删除异常：" + e.getMessage());
        }
    }

    @Override
    public Ship getById(Integer id) {
        try {
            Ship ship=shipMapper.getById(id);
            return ship;
        } catch (Exception e) {
            throw new BusinessException("通过id获取船只异常"+e.getMessage());
        }
    }

    @Override
    public Ship getByName(String name) {
        try {
            Ship ship=shipMapper.getByName(name);
            return ship;
        } catch (Exception e) {
            throw new BusinessException("通过name获取船只异常"+e.getMessage());
        }
    }

    @Override
    public List<ShipVO> getByType(String type) {
        try {
            if(!(type.equals("CARGO")||type.equals("FISHING")||type.equals("CRUISE"))){
                throw new BusinessException("船只类型无效");
            }
            List<ShipVO> ships=shipMapper.getByType(type);
            return ships;
        } catch (BusinessException e) {
            throw e;
        }catch (Exception e){
            throw new BusinessException("船只类型获取异常："+e.getMessage());
        }
    }

    @Override
    public boolean login(String shipName, String userName, String password) {
        try {
            Ship ship=shipMapper.getByName(shipName);
            Integer ownerId=ship.getOwnerId();
            Manager manager=managerMapper.getById(ownerId);
            if(!manager.getUsername().equals(userName)){
                throw new BusinessException("该船只不由"+userName+"管理");
            }
            if(!manager.getPassword().equals(password)){
                throw new BusinessException("密码错误");
            }
            System.out.println("登录成功");
            return true;
        } catch (BusinessException e) {
            throw e;
        }catch (Exception e){
            throw new BusinessException("登录异常："+e.getMessage());
        }
    }

    @Override
    public boolean isShipNameExist(String shipName) {
        try {
            Ship ship=shipMapper.getByName(shipName);
            System.out.println("船只"+shipName+"是否存在："+(ship!=null));
            return ship!=null;
        } catch (Exception e) {
            throw new BusinessException("判断船只是否存在异常："+e.getMessage());
        }
    }

    @Override
    public ShipCargoVO addProductToShip(Integer shipId, Integer productId, Integer cargoQuantity) {
        return null;
    }

    @Override
    public boolean deleteProductFromShip(Integer id) {
        return false;
    }

    @Override
    public boolean updateProductQuantity(Integer id, Integer cargoQuantity) {
        return false;
    }

    @Override
    public List<ProductVO> getAllProductsInShip(Integer shipId) {
        return List.of();
    }

    @Override
    public ProductVO getProductInShip(Integer shipId, Integer productId) {
        return null;
    }

    @Override
    public List<ProductVO> getAllAvailableProductsInShip(Integer shipId) {
        return List.of();
    }
}
