package org.example.port_manage_system.controller;

import org.example.port_manage_system.domain.dto.*;
import org.example.port_manage_system.domain.vo.ApiResultVO;
import org.example.port_manage_system.domain.vo.ManagerVO;
import org.example.port_manage_system.exception.BusinessException;
import org.example.port_manage_system.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    //注册功能
    @PostMapping("register")
    public ApiResultVO<ManagerVO> register(@RequestBody RegisterDTO registerDTO){
        try {
            boolean success=authService.register(registerDTO);
            if(success){
                ManagerVO vo=new ManagerVO();
                vo.setUsername(registerDTO.getUsername());
                vo.setPassword(registerDTO.getPassword());
                vo.setPhone(registerDTO.getPhone());
                vo.setUserType(registerDTO.getUserType());
                return ApiResultVO.success("注册成功",vo);
            }else{
                return ApiResultVO.error("注册失败");
            }
        } catch (Exception e) {
            return ApiResultVO.error("注册异常"+e.getMessage());
        }
    }

    //登录功能
    @PostMapping("login")
    public ApiResultVO<ManagerVO> login(@RequestBody LoginDTO loginDTO){
        try {
            if(loginDTO.getUsername()==null||loginDTO.getPassword()==null){
                return ApiResultVO.error("用户名或密码不能为空");
            }
            ManagerVO vo=authService.login(loginDTO);
            if(vo!=null){
                return ApiResultVO.success("登录成功",null);
            }else{
                return ApiResultVO.error("用户名或密码错误");
            }
        } catch (Exception e) {
            return ApiResultVO.error("登录异常"+e.getMessage());
        }
    }

    //修改密码功能
    @PostMapping("changePassword")
    public ApiResultVO<ManagerVO> changePassword(@RequestBody ChangePasswordDTO changePasswordData){
        try {
            boolean success=authService.changePassword(changePasswordData.getUsername(),changePasswordData.getOldPassword(),changePasswordData.getNewPassword());
            if(success){
                return ApiResultVO.success("修改密码成功", null);
            }else{
                return ApiResultVO.error("修改密码失败");
            }
        } catch (Exception e) {
            return ApiResultVO.error("修改密码异常"+e.getMessage());
        }
    }

    //注销功能
    @PostMapping("logout")
    public ApiResultVO<ManagerVO> logout(@RequestBody LogoutDTO logoutDTO){
        try {
            boolean success=authService.logout(logoutDTO.getUsername(),logoutDTO.getPassword());
            if(success){
                return ApiResultVO.success("注销成功", null);
            }else{
                return ApiResultVO.error("注销失败");
            }
        } catch (Exception e) {
            return ApiResultVO.error("注销异常"+e.getMessage());
        }
    }

    //查询用户名是否存在
    @PostMapping("isUserNameExist")
    public ApiResultVO<ManagerVO> isUserNameExist(@RequestBody UsernameExistsDTO usernameExistsDTO){
        try {
            if(usernameExistsDTO.getUsername()== null){
                return ApiResultVO.error("用户名不能为空");
            }
            boolean exists=authService.isUserNameExist(usernameExistsDTO.getUsername());
            return exists ? ApiResultVO.success("用户名已存在", null) : ApiResultVO.error("用户名不存在");
        } catch (Exception e) {
            return ApiResultVO.error("查询用户名是否存在异常"+e.getMessage());
        }
    }

    //查询用户是否以特定类型登录
    @PostMapping("isUserLoggedInAs")
    public ApiResultVO<ManagerVO> isUserLoggedInAs(@RequestBody UserIdentityDTO userIdentityDTO){
        try {
            if(userIdentityDTO.getUserType()==null){
                return ApiResultVO.error("用户类型不能为空");
            }
            boolean is=authService.isUserLoggedInAs(userIdentityDTO.getUsername(),userIdentityDTO.getPassword(),userIdentityDTO.getUserType());
            return is ? ApiResultVO.success("用户已以特定类型登录", null) : ApiResultVO.error("用户未以特定类型登录");
        } catch (Exception e) {
            return ApiResultVO.error("查询用户是否已以特定类型登录异常"+e.getMessage());
        }
    }



}
