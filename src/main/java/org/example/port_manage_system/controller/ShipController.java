package org.example.port_manage_system.controller;

import org.example.port_manage_system.domain.dto.*;
import org.example.port_manage_system.domain.entity.Ship;
import org.example.port_manage_system.domain.vo.*;
import org.example.port_manage_system.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("api/ship")
public class ShipController {

    @Autowired
    private ShipService shipService;
    @Autowired
    private InPortService inPortService;
    @Autowired
    private OutPortService outPortService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增船只
     * @param
     * @return
     */
    @PostMapping("api/ships")
    public ResultVO<ShipVO> addShip(@RequestBody ShipRequestDTO request){
        ShipResponseDTO response = shipService.addShip(request);
        ShipVO vo = new ShipVO();
        BeanUtils.copyProperties(response, vo);
        return ResultVO.success("创建成功", vo);
    }

//    //根据船只id删除船只
//    @DeleteMapping("deleteById")
//    public ApiResultVO<ShipVO> deleteById(@RequestParam Integer id){
//        try {
//            Ship ship=shipService.getById(id);
//            if(ship==null){
//                return ApiResultVO.error("删除船只失败，船只不存在");
//            }
//
//            boolean success=shipService.deleteById(id);
//            if(success){
//                return ApiResultVO.success("删除船只成功", null);
//            }else{
//                return ApiResultVO.error("删除船只失败");
//            }
//        } catch (Exception e) {
//            return ApiResultVO.error("删除船只异常"+e.getMessage());
//        }
//    }
//
//    //根据船只名称删除船只
//    @DeleteMapping("deleteByName")
//    public ApiResultVO<ShipVO> deleteByName(@RequestParam("name") String shipName){
//        try {
//            boolean success=shipService.deleteByName(shipName);
//            if(success){
//                return ApiResultVO.success("删除船只成功", null);
//            }else{
//                return ApiResultVO.error("删除船只失败");
//            }
//        } catch (Exception e) {
//            return ApiResultVO.error("删除船只异常"+e.getMessage());
//        }
//    }
//
//    //根据船只id获取船只
//    @GetMapping("getById")
//    public ApiResultVO<ShipVO> getById(@RequestParam Integer id){
//        try {
//            ShipVO vo=new ShipVO();
//            Ship ship=shipService.getById(id);
//            if(ship!=null){
//                vo.setId(ship.getId());
//                vo.setShipName(ship.getShipName());
//                vo.setOwnerId(ship.getOwnerId());
//                vo.setShipType(ship.getShipType());
//                vo.setShipSize(ship.getShipSize());
//                vo.setQualificationStatus(ship.getQualificationStatus());
//                return ApiResultVO.success("获取船只成功",vo);
//            }else{
//                return ApiResultVO.error("获取船只失败，船只不存在");
//            }
//        } catch (Exception e) {
//            return ApiResultVO.error("获取船只异常"+e.getMessage());
//        }
//    }
//
//    //根据船只名称获取船只
//    @GetMapping("getByName")
//    public ApiResultVO<ShipVO> getByName(@RequestParam String name){
//        try {
//            ShipVO vo=new ShipVO();
//            Ship ship=shipService.getByName(name);
//            if(ship!=null){
//                vo.setId(ship.getId());
//                vo.setShipName(ship.getShipName());
//                vo.setOwnerId(ship.getOwnerId());
//                vo.setShipType(ship.getShipType());
//                vo.setShipSize(ship.getShipSize());
//                vo.setQualificationStatus(ship.getQualificationStatus());
//                return ApiResultVO.success("获取船只成功",vo);
//            }else{
//                return ApiResultVO.error("获取船只失败，船只不存在");
//            }
//        } catch (Exception e) {
//            return ApiResultVO.error("获取船只异常"+e.getMessage());
//        }
//    }
//
//    //根据船只类型获取船只
//    @GetMapping("getByType")
//    public ApiResultVO<List<ShipVO>> getByType(@RequestParam String type){
//        try {
//            List<ShipVO> ships=shipService.getByType(type);
//            if (ships != null) {
//                return ApiResultVO.success("获取船只成功",ships);
//            }else{
//                return ApiResultVO.error("获取船只失败，该类型船只不存在");
//            }
//        } catch (Exception e) {
//            return ApiResultVO.error("获取船只异常"+e.getMessage());
//        }
//    }
//
//    //船只登录
//    @PostMapping("login")
//    public ApiResultVO<ShipVO> login(@RequestParam String shipName,String userName,String password){
//        try {
//            boolean success=shipService.login(shipName,userName,password);
//            if(success){
//                return ApiResultVO.success("登录成功", null);
//            }else{
//                return ApiResultVO.error("登录失败");
//            }
//        } catch (Exception e) {
//            return ApiResultVO.error("登录异常"+e.getMessage());
//        }
//    }
//
//    //申请入港
//    @PostMapping("applyInPort")
//    public ApiResultVO<InPortApplicationVO> applyInPort(@RequestParam String shipName){
//        try {
//            boolean success=inPortService.applyInPort(shipName);
//            if(success){
//                InPortApplicationVO vo=inPortService.getLatestApplication(shipName);
//                return ApiResultVO.success("入港申请成功", vo);
//            }else{
//                return ApiResultVO.error("入港申请失败");
//            }
//        } catch (Exception e) {
//            return ApiResultVO.error("申请入港异常"+e.getMessage());
//        }
//    }
//
//    //船只出港：获取出港时间
//    @PutMapping("getExitTime")
//    public ApiResultVO<InPortApplicationVO> getExitTime(@RequestParam String shipName){
//        try {
//            Ship ship=shipService.getByName(shipName);
//            if(ship==null){
//                return ApiResultVO.error("船只不存在");
//            }
//            Integer shipId=ship.getId();
//            boolean result = outPortService.updateExitTime(shipId);
//            if(result){
//                InPortDTO inPortDTO=inPortService.getByShipId(shipId);
//                InPortApplicationVO vo=new InPortApplicationVO();
//                BeanUtils.copyProperties(inPortDTO,vo);
//                return ApiResultVO.success("获取船只出港时间成功", vo);
//            }else{
//                return ApiResultVO.error("获取船只出港时间失败");
//            }
//        } catch (BeansException e) {
//            return ApiResultVO.error("获取船只出港时间异常"+e.getMessage());
//        }
//    }
//
//    //船长查询集市所有商品
//    @GetMapping("getAllMarketProducts")
//    public ApiResultVO<List<ProductVO>> getAllMarketProducts(){
//        try {
//            List<ProductVO> products = productService.getProducts();
//            if(products != null){
//                return ApiResultVO.success("查询集市所有商品成功", products);
//            }else{
//                return ApiResultVO.error("集市暂无商品");
//            }
//        }catch (Exception e) {
//            return ApiResultVO.error("查询集市所有商品异常"+e.getMessage());
//        }
//    }
//
//
//    //船长查询某商品
//    @GetMapping("getMarketProductByName")
//    public ApiResultVO<ProductVO> getMarketProductByName(@RequestParam String name){
//        try {
//            ProductVO productVO = productService.getProductByName(name);
//            if(productVO == null){
//                return ApiResultVO.error("集市暂无该商品");
//            }else{
//                return ApiResultVO.success("查询集市"+name+"商品成功", productVO);
//            }
//        } catch (Exception e) {
//            return ApiResultVO.error("查询集市该商品异常"+e.getMessage());
//        }
//    }
//
//    //船长查询所有有库存的商品
//    @GetMapping("getAllAvailable")
//    public ApiResultVO<List<ProductVO>> getAllAvailable(){
//        try {
//            List<ProductVO> productVOS = productService.getAllAvailable();
//            if(productVOS == null){
//                return ApiResultVO.error("集市暂无商品");
//            }else{
//                return ApiResultVO.success("查询集市所有有库存商品成功", productVOS);
//            }
//        } catch (Exception e) {
//            return ApiResultVO.error("查询集市所有有库存商品异常"+e.getMessage());
//        }
//    }

    //船长向船上添加商品
    //船长删除船上某商品
    //船长修改某商品数量
    //船长查询船上所有商品
    //船长查询船上某商品
    //船长查询船上所有有库存商品

    /**
     * 分页查询船只
     * @param
     * @return
     */
    @GetMapping("api/ships")
    public ResultVO<ShipPageDTO> getShips(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
//            @RequestParam(required = false) Integer ownerId,
            @RequestParam(required = false) String qualificationStatus) {

        ShipQueryDTO queryDTO = new ShipQueryDTO();
        queryDTO.setPage(page);
        queryDTO.setPageSize(pageSize);
//        queryDTO.setOwnerId(ownerId);
        queryDTO.setQualificationStatus(qualificationStatus);

        ShipPageDTO shipPageDTO = shipService.getShipList(queryDTO);
        return ResultVO.success(shipPageDTO);
    }
}
