package org.example.port_manage_system.controller;

import org.example.port_manage_system.domain.vo.ApiResultVO;
import org.example.port_manage_system.domain.vo.ResultVO;
import org.example.port_manage_system.domain.vo.UserListVO;
import org.example.port_manage_system.domain.vo.UserVO;
import org.example.port_manage_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户列表（按用户类型）
     * 接口：GET /api/users?userType=SHIP_OWNER
     */
    @GetMapping
    public ResultVO<UserListVO> getUsersByType(@RequestParam String userType) {
        try {
            if (userType == null || userType.trim().isEmpty()) {
                return ResultVO.error("userType参数必填");
            }

            // 验证用户类型是否合法
            if (!isValidUserType(userType)) {
                return ResultVO.error("userType参数不合法");
            }

            UserListVO result = userService.getUsersByType(userType);
            return ResultVO.success(result);
        } catch (Exception e) {
            return ResultVO.serverError("查询用户列表异常：" + e.getMessage());
        }
    }

    /**
     * 根据ID获取用户详情
     */
    @GetMapping("{id}")
    public ResultVO<UserVO> getUserById(@PathVariable Integer id) {
        try {
            if (id == null) {
                return ResultVO.error("用户ID不能为空");
            }

            UserVO user = userService.getUserById(id);
            if (user == null) {
                return ResultVO.notFound("用户不存在");
            }
            return ResultVO.success(user);
        } catch (Exception e) {
            return ResultVO.serverError("查询用户详情异常：" + e.getMessage());
        }
    }

    /**
     * 验证用户类型是否合法
     */
    private boolean isValidUserType(String userType) {
        String[] allowedTypes = {"SHIP_OWNER", "PORT_ADMIN", "MARKET_ADMIN"};
        for (String allowedType : allowedTypes) {
            if (allowedType.equals(userType)) {
                return true;
            }
        }
        return false;
    }
}