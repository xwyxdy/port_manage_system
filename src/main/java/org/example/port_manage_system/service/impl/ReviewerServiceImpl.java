package org.example.port_manage_system.service.impl;

import org.example.port_manage_system.domain.bo.InPortBO;
import org.example.port_manage_system.domain.bo.PaymentOrdersBO;
import org.example.port_manage_system.domain.bo.ReviewerBO;
import org.example.port_manage_system.domain.dto.InPortDTO;
import org.example.port_manage_system.domain.dto.PaymentOrdersDTO;
import org.example.port_manage_system.domain.entity.CargoShip;
import org.example.port_manage_system.domain.entity.InPort;
import org.example.port_manage_system.domain.entity.Product;
import org.example.port_manage_system.domain.entity.Ship;
import org.example.port_manage_system.domain.vo.InPortApplicationVO;
import org.example.port_manage_system.domain.vo.ProductVO;
import org.example.port_manage_system.exception.BusinessException;
import org.example.port_manage_system.mapper.ManagerMapper;
import org.example.port_manage_system.mapper.ReviewerMapper;
import org.example.port_manage_system.mapper.ShipMapper;
import org.example.port_manage_system.service.PaymentRecordsService;
import org.example.port_manage_system.service.ReviewerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ReviewerServiceImpl implements ReviewerService {

    @Autowired
    private ReviewerMapper reviewerMapper;
    @Autowired
    private ShipMapper shipMapper;
    @Autowired
    private ManagerMapper managerMapper;
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private PaymentOrdersServiceImpl paymentOrdersService;
    @Autowired
    private PaymentRecordsService paymentRecordsService;
    @Override
    public boolean updateReviewStatus(String reviewStatus, Integer shipId) {
        try {
            //先检查船只是否存在
            Ship ship=shipMapper.getById(shipId);
            if(ship==null){
                throw new BusinessException("船只"+shipId+"不存在");
            }
            //再检查船只是否已提交入港申请
            InPortDTO inPortDTO=shipMapper.getLatestApplicationByShipId(shipId);
            InPortBO inportBO=new InPortBO();
            if(!inportBO.isSubmitted(inPortDTO.getApplicationStatus())){
                throw new BusinessException("船只"+shipId+"未提交入港申请");
            }
            //检查审核意见是否合法
            if(!inportBO.isValidReviewStatusOnlyEnum(reviewStatus)){
                throw new BusinessException("无效的审核意见");
            }
            boolean result=reviewerMapper.updateReviewStatus(reviewStatus,shipId);
            if( result){
                System.out.println("审核意见提交成功");
                return true;
            }else{
                System.out.println("审核意见提交失败");
                return false;
            }
        }catch (BusinessException e){
            throw e;
        } catch(Exception e) {
            System.out.println("审核意见提交异常"+e.getMessage());
            throw new BusinessException("审核意见提交异常"+e.getMessage());
        }
    }

    @Override
    public boolean updateReviewerId(Integer reviewerId, Integer shipId) {
        try {
            //先检查船只是否存在
            Ship ship=shipMapper.getById(shipId);
            if(ship==null){
                throw new BusinessException("船只"+shipId+"不存在");
            }
            //再检查审核员id
            if(managerMapper.getById(reviewerId)==null){
                throw new BusinessException("管理员"+reviewerId+"不存在");
            }
            ReviewerBO reviewerBO=new ReviewerBO();
            if(!reviewerBO.isPortAdmin(managerMapper.getById(reviewerId).getUserType())){
                throw new BusinessException("管理员"+reviewerId+"不是港口管理员");
            }
            //再检查船只申请是否已提交
            InPortDTO inPortDTO=shipMapper.getLatestApplicationByShipId(shipId);
            InPortBO inportBO=new InPortBO();
            if(!inportBO.isSubmitted(inPortDTO.getApplicationStatus())){
                throw new BusinessException("船只"+shipId+"未提交入港申请");
            }
            boolean result=reviewerMapper.updateReviewerId(reviewerId,shipId);
            if( result){
                System.out.println("审核员id提交成功");
                return true;
            }else{
                System.out.println("审核员id提交失败");
                return false;
            }
        }catch (BusinessException e){
            throw e;
        } catch (Exception e) {
            System.out.println("审核员id提交异常"+e.getMessage());
            throw new BusinessException("审核员id提交异常"+e.getMessage());
        }
    }

    @Override
    public List<InPortApplicationVO> getByReview(String reviewStatus) {
        try {
            //检查审核状态
            InPortBO inPortBO=new InPortBO();
            if(!inPortBO.isValidReviewStatusOnlyEnum(reviewStatus)){
                throw new BusinessException("无效的审核状态");
            }
            List<InPortApplicationVO> result= reviewerMapper.getByReview(reviewStatus);
            if(result!=null){
                System.out.println("查询成功");
                return result;
            }else{
                throw new BusinessException("查询失败，不存在审核状态为"+reviewStatus+"的船只");
            }
        }catch (BusinessException e){
            throw e;
        }catch (Exception e) {
            System.out.println("查询异常"+e.getMessage());
            throw new BusinessException("查询异常"+e.getMessage());
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        try {
            //确认表中存在该条数据
            Ship ship=shipMapper.getById(id);
            if(ship==null){
                throw new BusinessException("删除失败，入港申请表中无该条数据");
            }
            //确认船只已离港
            InPortBO inPortBO=new InPortBO();
            InPortDTO inPortDTO=shipMapper.getLatestApplicationByShipId(id);
            if(!inPortBO.isDeparted(inPortDTO)){
                throw new BusinessException("删除失败，船只"+ship.getShipName()+"未离港");
            }
            //检查支付状态
            PaymentOrdersBO paymentOrdersBO=new PaymentOrdersBO();
            PaymentOrdersDTO paymentOrders = paymentOrdersService.getOrdersByEntryId(inPortDTO.getId());
            if(!paymentOrdersBO.isPaid(paymentOrders.getPaymentStatus())){
                throw new BusinessException("删除失败，船只"+ship.getShipName()+"未支付");
            }
            //若已支付，则先在支付表中删除数据
            boolean y=paymentRecordsService.deletePaymentRecordByOrderId(paymentOrders.getId());
            if(!y){
                throw new BusinessException("删除失败，支付记录删除失败");
            }
            //再在订单表中删除该条数据
            boolean r = paymentOrdersService.deleteOrdersByEntryId(inPortDTO.getId());
            if(!r){
                throw new BusinessException("删除失败，支付订单删除失败");
            }
            boolean result=reviewerMapper.deleteById(id);
            if(result){
                System.out.println("删除成功");
                return true;
            }else{
                throw new BusinessException("删除失败");
            }
        }catch (BusinessException e){
            throw e;
        }catch (Exception e) {
            System.out.println("删除异常"+e.getMessage());
            throw new BusinessException("删除异常"+e.getMessage());
        }
    }

    @Override
    public List<ProductVO> getAllGoodsInCargo(Integer shipId) {
        try {
            //检查船只是否存在
            Ship ship=shipMapper.getById(shipId);
            if(ship==null){
                throw new BusinessException("船只"+shipId+"不存在");
            }
            //检查船只是否是货船
            if(!ship.getShipType().equals("CARGO")){
                throw new BusinessException("船只"+shipId+"不是货船");
            }
            List<CargoShip> cargoShips= reviewerMapper.getAllGoodsInCargo(shipId);
            if(cargoShips==null){
                throw new BusinessException("船只"+shipId+"无货物");
            }
            List<ProductVO> products=new ArrayList<>();
            for(CargoShip cargoShip:cargoShips){
                //检查货物数量是否大于0
                if(cargoShip.getCargoQuantity()>0){
                    ProductVO productVO=productService.getProductById(cargoShip.getProductId());
                    if(productVO!=null){
                        products.add(productVO);
                    }
                }
                if(products==null){
                   throw new BusinessException("船只"+shipId+"无货物");
                }
            }
            return products;
        }catch (BusinessException e){
            throw e;
        } catch (Exception e) {
            System.out.println("查询货船货物异常"+e.getMessage());
            throw new BusinessException("查询货船货物异常"+e.getMessage());
        }
    }
}
