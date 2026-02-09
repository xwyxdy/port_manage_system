package org.example.port_manage_system.service;

import org.example.port_manage_system.domain.vo.InPortApplicationVO;

public interface InPortService {


    //在入港申请表中删除船只信息
    boolean deleteByShipId(Integer id);

    //提交入港申请
    boolean applyInPort(String shipName);

    //获取最新的入港申请
    InPortApplicationVO getLatestApplication(String shipName);
}
