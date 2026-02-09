package org.example.port_manage_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.example.port_manage_system.domain.bo.ManagerBO;
import org.example.port_manage_system.domain.dto.LoginDTO;
import org.example.port_manage_system.domain.dto.RegisterDTO;
import org.example.port_manage_system.domain.vo.ManagerVO;
import org.example.port_manage_system.mapper.ManagerMapper;
import org.example.port_manage_system.domain.entity.Manager;
import org.example.port_manage_system.service.AuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    @Autowired
    private ManagerMapper managerMapper;


    //注册功能
    @Override
    public boolean register(RegisterDTO registerDTO) {
        try {
            if(isUserNameExist(registerDTO.getUsername())) {
                System.out.println("用户名"+registerDTO.getUsername()+"已存在");
                return false;
            }
            //DTO->entity
            Manager manager=new Manager();
            BeanUtils.copyProperties(registerDTO,manager);

            //使用BO业务进行验证
            ManagerBO managerBO=new ManagerBO(manager);
            if(!managerBO.isValidUserType()){
                System.out.println("用户类型"+manager.getUserType()+"无效");
                return false;
            }

            if(!managerBO.isValidPassword()){
                System.out.println("密码"+manager.getPassword()+"不足六位，无效");
                return false;
            }

            int result=managerMapper.insert(manager);
            if (result > 0) {
                System.out.println("用户" + registerDTO.getUsername() + "注册成功");
                return true;
            } else {
                System.out.println("用户" + registerDTO.getUsername() + "注册失败");
                return false;
            }
        } catch (BeansException e) {
            System.out.println("注册异常：" + e.getMessage());
            return false;
        }

    }

    //登录功能
    @Override
    public ManagerVO login(LoginDTO loginDTO) {
        try {
            if(loginDTO.getUsername()==null||loginDTO.getPassword()==null){
                System.out.println("用户名或密码不能为空");
                return null;
            }

            Manager manager = managerMapper.getByUsername(loginDTO.getUsername());

            if(manager==null){
                System.out.println("用户不存在");
                return null;
            }
            if(!manager.getPassword().equals(loginDTO.getPassword())){
                System.out.println("密码错误");
                return null;
            } else {
                System.out.println("用户"+loginDTO.getUsername()+"登录成功");
                //entity->VO
                ManagerVO managerVO=new ManagerVO();
                BeanUtils.copyProperties(manager,managerVO);
                managerVO.setPassword(null);//不返回密码
                return managerVO;
            }
        } catch (BeansException e) {
            System.out.println("登录异常：" + e.getMessage());
            return null;
        }
    }


    //修改密码功能
    @Override
    public boolean changePassword(String userName, String oldPassword, String newPassword) {
        try {
            if(userName==null||oldPassword==null||newPassword==null){
                System.out.println("用户名或密码不能为空");
                return false;
            }
            Manager manager=managerMapper.getByUsername(userName);
            if(manager==null){
                System.out.println("用户不存在");
                return false;
            }
            if(!manager.getPassword().equals(oldPassword)){
                System.out.println("密码错误");
                return false;
            }
            if(manager.getPassword().equals(newPassword)){
                System.out.println("新密码不能与旧密码相同");
                return false;
            }
            manager.setPassword(newPassword);
            int result=managerMapper.update( manager);
            if (result > 0) {
                System.out.println("用户" + userName + "修改密码成功");
                return true;
            } else {
                System.out.println("用户" + userName + "修改密码失败");
                return false;
            }
        } catch (Exception e) {
            System.out.println("修改密码异常：" + e.getMessage());
            return false;
        }
    }


    //注销功能
    @Override
    public boolean logout(String userName, String password) {
        Manager manager=managerMapper.getByUsername(userName);
        if(manager==null){
            System.out.println("用户不存在");
            return false;
        }
        if(!manager.getPassword().equals(password)){
            System.out.println("注销失败，密码错误");
            return false;
        }
        int result=managerMapper.delete(manager.getId());
        if (result > 0) {
            System.out.println("用户" + userName + "注销成功");
            return true;
        }
        return false;
    }

    //判断用户名是否已存在
    @Override
    public boolean isUserNameExist(String username) {
        try {
            Manager manager=managerMapper.getByUsername(username);
            System.out.println("用户"+username+"是否存在："+(manager!=null));
            return manager!=null;
        } catch (Exception e) {
            System.out.println("判断用户名是否存在异常：" + e.getMessage());
            return false;
        }
    }

    //判断用户是否已特定类型登录
    @Override
    public boolean isUserLoggedInAs(String username, String password, String requiredUserType) {
        try {
            Manager manager=managerMapper.getByUsername(username);
            boolean success=manager!=null&&manager.getPassword().equals(password)&&manager.getUserType().equals(requiredUserType);
            System.out.println("用户"+username+"是否已登录："+success);
            return success;
        } catch (Exception e) {
            System.out.println("判断用户是否已登录异常：" + e.getMessage());
            return false;
        }
    }

}
