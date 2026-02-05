package org.example.port_manage_system.service;

import org.example.port_manage_system.domain.dto.LoginDTO;
import org.example.port_manage_system.domain.dto.RegisterDTO;
import org.example.port_manage_system.domain.vo.ManagerVO;

public interface AuthService {

    //用户注册
    boolean register(RegisterDTO registerDTO);

    //用户登录
    ManagerVO login(LoginDTO loginDTO);

    //用户修改密码
    boolean changePassword(String userName,String oldPassword,String newPassword);

    //用户注销
    boolean logout(String userName,String  password);

    //检查用户名是否已经存在
    boolean isUserNameExist(String username);

    //检查用户是否已经以特定身份登录
    boolean isUserLoggedInAs(String username, String password,String requiredUserType);

}
