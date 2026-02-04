package org.example.port_manage_system.controller;

import org.example.port_manage_system.pojo.Manager;
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

    @PostMapping("register")
    public Map<String,Object> register(@RequestBody Manager manager){
        Map<String,Object> result=new HashMap<>();
        boolean success=authService.register(manager);
        try {
            if( success){
                result.put("success",true);
                result.put("message","注册成功");
                result.put("timeStamp",new Date());
                manager.setPassword(null);
                result.put("data",manager);
                result.put("note","请使用注册的用户名密码进行登录，后续操作都需要提供用户名密码");
            }else{
                result.put("success",false);
                result.put("message","注册失败");
            }
        } catch (Exception e) {
            result.put("success",false);
            result.put("message","注册异常"+e.getMessage());
        }
        return result;
    }

    @PostMapping("login")
    public Map<String,Object> login(@RequestBody Map<String,String> logInData){
        Map<String,Object> result=new HashMap<>();
        String username=logInData.get("username");
        String password=logInData.get("password");
        try {
            if(username==null || password==null){
                result.put("success",false);
                result.put("message","用户名或密码不能为空");
            }
            Manager manager=authService.login(username,password);
            if(manager!=null){
                result.put("success",true);
                result.put("message","登录成功");
                result.put("timeStamp",new Date());
                manager.setPassword(null);
                result.put("data",manager);
            }else{
                result.put("success",false);
            }
        }catch (Exception e){
            result.put("success",false);
            result.put("message","登录异常"+e.getMessage());
        }
        return result;
    }

    @PostMapping("changePassword/{changePasswordData}")
    public Map<String,Object> changePassword(@PathVariable Map<String,String> changePasswordData){
        Map<String,Object> result=new HashMap<>();
        String username=changePasswordData.get("username");
        String oldPassword=changePasswordData.get("oldPassword");
        String newPassword=changePasswordData.get("newPassword");
        try {
            boolean success=authService.changePassword(username,oldPassword,newPassword);
            if(success){
                result.put("success",true);
                result.put("message","修改密码成功");
                result.put("timeStamp",new Date());
                result.put("data",null);
                result.put("note","请使用修改后的密码进行登录");
            }else{
                result.put("success",false);
                result.put("message","修改密码失败");
            }
        } catch (Exception e) {
            result.put("success",false);
            result.put("message","修改密码异常"+e.getMessage());
        }
        return result;
    }

    @PostMapping("logout")
    public Map<String,Object> logout(@RequestBody Map<String,String> logoutData){
        String username=logoutData.get("username");
        String password=logoutData.get("password");
        Map<String,Object> result=new HashMap<>();
        try {
            boolean success=authService.logout(username,password);
            if(success){
                result.put("success",true);
                result.put("message","注销成功");
                result.put("timeStamp",new Date());
                result.put("data",null);
            }else{
                result.put("success",false);
                result.put("message","注销失败");
            }
        }catch (Exception e){
            result.put("success",false);
            result.put("message","注销异常"+e.getMessage());
        }
        return result;
    }

    @PostMapping("isUserNameExist/{username}")
    public Map<String,Object> isUserNameExist(@PathVariable String username){
        Map<String,Object> result=new HashMap<>();
        try {
            if(username==null){
                result.put("success",false);
                result.put("message","用户名不能为空");
            }
            boolean exist=authService.isUserNameExist(username);
            if(exist){
                result.put("success",true);
                result.put("message","用户名已存在");
                result.put("timeStamp",new Date());
                result.put("data",null);
            }else{
                result.put("success",false);
                result.put("message","用户名不存在");
            }
        }catch (Exception e){
            result.put("success",false);
            result.put("message","查询用户名异常"+e.getMessage());
        }
        return result;
    }

    @PostMapping("isUserLoggedInAs/{loginData}")
    public Map<String,Object> isUserLoggedInAs(@PathVariable Map<String,String> loginData){
        String username=loginData.get("username");
        String password=loginData.get("password");
        String requiredUserType=loginData.get("requiredUserType");
        Map<String,Object> result=new HashMap<>();
        try {
            boolean success=authService.isUserLoggedInAs(username,password,requiredUserType);
            if(success){
                result.put("success",true);
                result.put("message","该用户已登录");
                result.put("timeStamp",new Date());
                result.put("data",null);
            }else{
                result.put("success",false);
                result.put("message","用户类型错误");
            }
        }catch (Exception e){
            result.put("success",false);
            result.put("message","查询用户登录状态异常"+e.getMessage());
        }
        return result;
    }



}
