package org.example.port_manage_system.controller;

import org.example.port_manage_system.domain.dto.InPortDTO;
import org.example.port_manage_system.domain.dto.ShipDTO;
import org.example.port_manage_system.domain.entity.Ship;
import org.example.port_manage_system.domain.vo.ApiResultVO;
import org.example.port_manage_system.domain.vo.InPortApplicationVO;
import org.example.port_manage_system.domain.vo.ShipVO;
import org.example.port_manage_system.service.InPortService;
import org.example.port_manage_system.service.ShipService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ship")
public class ShipController {

    @Autowired
    private ShipService shipService;
    @Autowired
    private InPortService inPortService;

    //新增船只
    @PostMapping("add")
    public ApiResultVO<ShipVO> addShip(@RequestBody ShipDTO shipDTO){
        try {
            boolean success=shipService.addShip(shipDTO);
            if(success){
                ShipVO vo=new ShipVO();
                vo.setId(shipDTO.getId());
                vo.setShipName(shipDTO.getShipName());
                vo.setOwnerId(shipDTO.getOwnerId());
                vo.setShipType(shipDTO.getShipType());
                vo.setShipSize(shipDTO.getShipSize());
                vo.setQualificationStatus(shipDTO.getQualificationStatus());
                return ApiResultVO.success("新增船只成功",vo);
            }else{
                return ApiResultVO.error("新增船只失败");
            }
        } catch (Exception e) {
            return ApiResultVO.error("新增船只异常"+e.getMessage());
        }
    }

    //根据船只id删除船只
    @DeleteMapping("deleteById")
    public ApiResultVO<ShipVO> deleteById(@RequestParam Integer id){
        try {
            Ship ship=shipService.getById(id);
            if(ship==null){
                return ApiResultVO.error("删除船只失败，船只不存在");
            }

            boolean success=shipService.deleteById(id);
            if(success){
                return ApiResultVO.success("删除船只成功", null);
            }else{
                return ApiResultVO.error("删除船只失败");
            }
        } catch (Exception e) {
            return ApiResultVO.error("删除船只异常"+e.getMessage());
        }
    }

    //根据船只名称删除船只
    @DeleteMapping("deleteByName")
    public ApiResultVO<ShipVO> deleteByName(@RequestParam("name") String shipName){
        try {
            boolean success=shipService.deleteByName(shipName);
            if(success){
                return ApiResultVO.success("删除船只成功", null);
            }else{
                return ApiResultVO.error("删除船只失败");
            }
        } catch (Exception e) {
            return ApiResultVO.error("删除船只异常"+e.getMessage());
        }
    }

    //根据船只id获取船只
    @GetMapping("getById")
    public ApiResultVO<ShipVO> getById(@RequestParam Integer id){
        try {
            ShipVO vo=new ShipVO();
            Ship ship=shipService.getById(id);
            if(ship!=null){
                vo.setId(ship.getId());
                vo.setShipName(ship.getShipName());
                vo.setOwnerId(ship.getOwnerId());
                vo.setShipType(ship.getShipType());
                vo.setShipSize(ship.getShipSize());
                vo.setQualificationStatus(ship.getQualificationStatus());
                return ApiResultVO.success("获取船只成功",vo);
            }else{
                return ApiResultVO.error("获取船只失败，船只不存在");
            }
        } catch (Exception e) {
            return ApiResultVO.error("获取船只异常"+e.getMessage());
        }
    }

    //根据船只名称获取船只
    @GetMapping("getByName")
    public ApiResultVO<ShipVO> getByName(@RequestParam String name){
        try {
            ShipVO vo=new ShipVO();
            Ship ship=shipService.getByName(name);
            if(ship!=null){
                vo.setId(ship.getId());
                vo.setShipName(ship.getShipName());
                vo.setOwnerId(ship.getOwnerId());
                vo.setShipType(ship.getShipType());
                vo.setShipSize(ship.getShipSize());
                vo.setQualificationStatus(ship.getQualificationStatus());
                return ApiResultVO.success("获取船只成功",vo);
            }else{
                return ApiResultVO.error("获取船只失败，船只不存在");
            }
        } catch (Exception e) {
            return ApiResultVO.error("获取船只异常"+e.getMessage());
        }
    }

    //根据船只类型获取船只
    @GetMapping("getByType")
    public ApiResultVO<List<ShipVO>> getByType(@RequestParam String type){
        try {
            List<ShipVO> ships=shipService.getByType(type);
            if (ships != null) {
                return ApiResultVO.success("获取船只成功",ships);
            }else{
                return ApiResultVO.error("获取船只失败，该类型船只不存在");
            }
        } catch (Exception e) {
            return ApiResultVO.error("获取船只异常"+e.getMessage());
        }
    }

    //船只登录
    @PostMapping("login")
    public ApiResultVO<ShipVO> login(@RequestParam String shipName,String userName,String password){
        try {
            boolean success=shipService.login(shipName,userName,password);
            if(success){
                return ApiResultVO.success("登录成功", null);
            }else{
                return ApiResultVO.error("登录失败");
            }
        } catch (Exception e) {
            return ApiResultVO.error("登录异常"+e.getMessage());
        }
    }

    //申请入港
    @PostMapping("applyInPort")
    public ApiResultVO<InPortApplicationVO> applyInPort(@RequestParam String shipName){
        try {
            boolean success=inPortService.applyInPort(shipName);
            if(success){
                InPortApplicationVO vo=inPortService.getLatestApplication(shipName);
                return ApiResultVO.success("入港申请成功", vo);
            }else{
                return ApiResultVO.error("入港申请失败");
            }
        } catch (Exception e) {
            return ApiResultVO.error("申请入港异常"+e.getMessage());
        }
    }
}
