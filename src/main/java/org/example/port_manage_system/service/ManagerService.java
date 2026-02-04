package org.example.port_manage_system.service;

import org.example.port_manage_system.pojo.Manager;

import java.util.List;

public interface ManagerService {

    //添加管理员
    int addManager(Manager manager);

    //更新管理员
    int updateManager(Manager manager);

    //删除管理员
    int deleteManager(Integer id);

    //根据id查询管理员
    Manager getManagerById(Integer id);

    //根据用户名查询管理员
    Manager getManagerByName(String name);

    //根据用户类型查询管理员
    Manager getManagerByType(String userType);

    //查询所有管理员
    List<Manager> getManagers();

    //查询所有姓张的用户
    List<Manager> getAllZhang();
}
