package org.example.port_manage_system.service;

import org.example.port_manage_system.pojo.Manager;

public interface AuthService {

    //用户注册
    boolean register(Manager manager);

    //用户登录
    Manager login(String username, String password);

    //用户修改密码
    boolean changePassword(String userName,String oldPassword,String newPassword);

    //用户注销
    boolean logout(String userName,String  password);

    //检查用户名是否已经存在
    boolean isUserNameExist(String username);

    //检查用户是否已经以特定身份登录
    boolean isUserLoggedInAs(String username, String password,String requiredUserType);

    //检查用户类型是否有效
    boolean isUserTypeValid(String userType);
}
