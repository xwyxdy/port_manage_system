package org.example.port_manage_system.service.impl;


import org.example.port_manage_system.domain.bo.InPortBO;
import org.example.port_manage_system.domain.dto.InPortDTO;
import org.example.port_manage_system.domain.entity.Ship;
import org.example.port_manage_system.domain.vo.InPortApplicationVO;
import org.example.port_manage_system.exception.BusinessException;
import org.example.port_manage_system.mapper.InPortMapper;
import org.example.port_manage_system.mapper.ShipMapper;
import org.example.port_manage_system.service.InPortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InPortServiceImpl implements InPortService {

    @Autowired
    private InPortMapper inPortMapper;
    @Autowired
    private ShipMapper shipMapper;
    //在入港申请表中删除船只信息
    @Override
    public boolean deleteByShipId(Integer id) {
        try {
            boolean result=inPortMapper.deleteByShipId(id);
            if(result){
                return true;
            }else{
                throw new BusinessException("根据船只id在入港申请表中删除船只失败");
            }
        } catch (BusinessException e) {
            throw e;
        }catch (Exception e){
            throw new BusinessException("根据船只id在入港申请表中删除船只异常");
        }
    }

    //提交入港申请
    @Override
    public boolean applyInPort(String shipName) {
        try {
            Ship ship=shipMapper.getByName(shipName);
            if(ship==null){
                throw new BusinessException("船只不存在");
            }
            // 2. 创建申请DTO
            InPortDTO dto = new InPortDTO();
            dto.setShipId(ship.getId());
            dto.setShipName(shipName);
            dto.setApplicationStatus("SUBMITTED");
            dto.setReviewStatus("PENDING");
            InPortBO inPortBO=new InPortBO(dto);
            if(!inPortBO.isValidApplicationStatus()){
                throw new BusinessException("船只"+ship.getShipName()+"申请入港申请状态无效");
            }
            if(!inPortBO.isValidReviewStatus()){
                throw new BusinessException("船只"+ship.getShipName()+"申请入港审核状态无效");
            }
            int result=shipMapper.applyInPort(dto);
            //将船只状态改为PENDING
            int r=shipMapper.updateQualificationStatus(ship.getId());
            if(result>0&&r>0){
                System.out.println("船只"+ship.getShipName()+"申请入港成功");
                return true;
            }else{
                throw new BusinessException("船只"+ship.getShipName()+"申请入港失败");
            }
        }catch (BusinessException e){
            throw e;
        }catch (Exception e) {
            throw new BusinessException("申请入港异常"+e.getMessage());
        }
    }

    @Override
    public InPortApplicationVO getLatestApplication(String shipName) {
        try {
            // 1. 查询船只
            Ship ship = shipMapper.getByName(shipName);
            if (ship == null) {
                throw new BusinessException("船只不存在");
            }

            // 2. 查询最新申请
            InPortDTO dto = shipMapper.getLatestApplicationByShipId(ship.getId());
            if (dto == null) {
                throw new BusinessException("船只没有申请入港");
            }

            // 3. 转换为 VO
            InPortApplicationVO vo = new InPortApplicationVO();
            vo.setId(dto.getId());
            vo.setShipId(dto.getShipId());
            vo.setShipName(shipName);
            vo.setEntryTime(dto.getEntryTime());
            vo.setExitTime(dto.getExitTime());
            vo.setApplicationStatus(dto.getApplicationStatus());
            vo.setReviewStatus(dto.getReviewStatus());
            vo.setReviewerId(dto.getReviewerId());

            return vo;
        }catch (BusinessException e){
            throw e;
        }catch (Exception e) {
            throw new BusinessException("查询异常"+e.getMessage());
        }
    }

    //在入港申请表中根据船只id查询船只
    @Override
    public InPortDTO getByShipId(Integer id) {
        return inPortMapper.getByShipId(id);
    }

    //在入港申请表中根据主键id查询入港记录
    @Override
    public InPortDTO getById(Integer id) {
        return inPortMapper.getById(id);
    }
}
