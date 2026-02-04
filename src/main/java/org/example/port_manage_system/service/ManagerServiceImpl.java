package org.example.port_manage_system.service;


import org.example.port_manage_system.mapper.ManagerMapper;
import org.example.port_manage_system.pojo.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service  //业务层Bean,会被Spring容器管理
@Transactional  //启用事务管理，出现异常回滚
public class ManagerServiceImpl implements ManagerService {

    //Spring容器会自动查找ManagerMapper类型的Bean并注入到managerMapper中
    @Autowired
    private ManagerMapper managerMapper;


    @Override
    public int addManager(Manager manager) {
        return managerMapper.insert(manager)>0?1:0;
    }

    @Override
    public int updateManager(Manager manager) {
        return managerMapper.update(manager)>0?1:0;
    }

    @Override
    public int deleteManager(Integer id) {
        return managerMapper.delete(id)>0?1:0;
    }

    @Override
    public Manager getManagerById(Integer id) {
        return managerMapper.getById(id);
    }

    @Override
    public Manager getManagerByName(String name) {
        return managerMapper.getByUsername(name);
    }

    @Override
    public Manager getManagerByType(String userType) {
        return managerMapper.getByUserType(userType);
    }

    @Override
    public List<Manager> getManagers() {
        return managerMapper.getAll();
    }

    @Override
    public List<Manager> getAllZhang() {
        return managerMapper.getAllZhang();
    }
}
