package org.example.port_manage_system.service.impl;


import org.example.port_manage_system.domain.bo.InPortBO;
import org.example.port_manage_system.domain.dto.InPortDTO;
import org.example.port_manage_system.domain.entity.Ship;
import org.example.port_manage_system.domain.vo.InPortApplicationVO;
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
        boolean result=inPortMapper.deleteByShipId(id);
        if(result){
            return true;
        }
        return false;
    }

    //提交入港申请
    @Override
    public boolean applyInPort(String shipName) {
        try {
            Ship ship=shipMapper.getByName(shipName);
            if(ship==null){
                System.out.println("船只"+ship.getShipName()+"不存在");
                return false;
            }
            // 2. 创建申请DTO
            InPortDTO dto = new InPortDTO();
            dto.setShipId(ship.getId());
            dto.setShipName(shipName);
            dto.setApplicationStatus("SUBMITTED");
            dto.setReviewStatus("PENDING");
            InPortBO inPortBO=new InPortBO(dto);
            if(!inPortBO.isValidApplicationStatus()){
                System.out.println("船只"+ship.getShipName()+"申请入港状态无效");
                return false;
            }
            if(!inPortBO.isValidReviewStatus()){
                System.out.println("船只"+ship.getShipName()+"申请入港审核状态无效");
                return false;
            }
            int result=shipMapper.applyInPort(dto);
            //将船只状态改为PENDING
            int r=shipMapper.updateQualificationStatus(ship.getId());
            if(result>0&&r>0){
                System.out.println("船只"+ship.getShipName()+"申请入港成功");
                return true;
            }else{
                System.out.println("船只"+ship.getShipName()+"申请入港失败");
                return false;
            }
        } catch (Exception e) {
            System.out.println("申请入港异常：" + e.getMessage());
            return false;
        }
    }

    @Override
    public InPortApplicationVO getLatestApplication(String shipName) {
        try {
            // 1. 查询船只
            Ship ship = shipMapper.getByName(shipName);
            if (ship == null) {
                return null;
            }

            // 2. 查询最新申请
            InPortDTO dto = shipMapper.getLatestApplicationByShipId(ship.getId());
            if (dto == null) {
                return null;
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
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //在入港申请表中根据船只id查询船只
    @Override
    public Ship getByShipId(Integer id) {
        return inPortMapper.getByShipId(id);
    }

    //在入港申请表中根据主键id查询入港记录
    @Override
    public InPortDTO getById(Integer id) {
        return inPortMapper.getById(id);
    }
}
