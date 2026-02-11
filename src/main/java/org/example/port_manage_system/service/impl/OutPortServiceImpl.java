package org.example.port_manage_system.service.impl;

import org.example.port_manage_system.domain.dto.InPortDTO;
import org.example.port_manage_system.domain.entity.Ship;
import org.example.port_manage_system.exception.BusinessException;
import org.example.port_manage_system.mapper.OutPortMapper;
import org.example.port_manage_system.service.OutPortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OutPortServiceImpl implements OutPortService {

    @Autowired
    private OutPortMapper outPortMapper;
    @Autowired
    private ShipServiceImpl shipService;
    @Autowired
    private InPortServiceImpl inPortService;
    @Override
    public boolean updateExitTime(Integer shipId) {
        try {
            //确认船只存在
            Ship ship=shipService.getById(shipId);
            if(ship==null){
                throw new BusinessException("船只不存在");
            }
            //确认船只在入港申请表中
            InPortDTO shipInApplication = inPortService.getByShipId(shipId);
            if(shipInApplication==null){
                throw new BusinessException("船只未在入港申请表中");
            }
            //确认船只入港时间不为空
            if (shipInApplication.getEntryTime()==null) {
                throw new BusinessException("船只未入港");
            }
            //确认船只出港时间为空
            if (shipInApplication.getExitTime()!=null) {
                throw new BusinessException("船只已出港");
            }
            //确认船只申请状态为已提交
            if (!shipInApplication.getApplicationStatus().equals("SUBMITTED")) {
                throw new BusinessException("船只未提交入港申请");
            }
            //确认船只审核状态为通过
            if (!shipInApplication.getReviewStatus().equals("APPROVED")) {
                throw new BusinessException("船只未通过入港审核");
            }
            boolean result=outPortMapper.updateExitTime(shipId);
            if(result){
                System.out.println("船只"+ship.getShipName()+"已出港");
                return true;
            }else{
                throw new BusinessException("船只"+ship.getShipName()+"出港失败");
            }
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("船只出港异常"+e.getMessage());
        }
    }
}
