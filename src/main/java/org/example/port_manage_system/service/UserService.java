package org.example.port_manage_system.service;

import org.example.port_manage_system.domain.vo.UserListVO;
import org.example.port_manage_system.domain.vo.UserVO;

public interface UserService {

    UserListVO getUsersByType(String userType);

    UserVO getUserById(Integer id);

    boolean checkUsernameExists(String username);
}
