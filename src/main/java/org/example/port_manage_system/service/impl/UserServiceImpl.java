package org.example.port_manage_system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.example.port_manage_system.domain.vo.UserListVO;
import org.example.port_manage_system.domain.vo.UserVO;
import org.example.port_manage_system.mapper.UserMapper;
import org.example.port_manage_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public UserListVO getUsersByType(String userType) {
        // 查询用户列表
        List<UserVO> userList = userMapper.selectByUserType(userType);

        // 处理可能的空列表
        if (CollectionUtils.isEmpty(userList)) {
            return new UserListVO(userList, 0);
        }

        return new UserListVO(userList, userList.size());
    }

    @Override
    public UserVO getUserById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public boolean checkUsernameExists(String username) {
        Integer count = userMapper.checkUsernameExists(username);
        return count != null && count > 0;
    }
}
