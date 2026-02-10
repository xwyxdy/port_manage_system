package org.example.port_manage_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.example.port_manage_system.domain.bo.ManagerBO;
import org.example.port_manage_system.domain.dto.LoginDTO;
import org.example.port_manage_system.domain.dto.RegisterDTO;
import org.example.port_manage_system.domain.vo.ManagerVO;
import org.example.port_manage_system.exception.BusinessException;
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
                throw new BusinessException("用户名"+registerDTO.getUsername()+"已存在");
            }
            //DTO->entity
            Manager manager=new Manager();
            BeanUtils.copyProperties(registerDTO,manager);

            //使用BO业务进行验证
            ManagerBO managerBO=new ManagerBO(manager);
            if(!managerBO.isValidUserType()){
                throw new BusinessException("用户类型"+manager.getUserType()+"无效");
            }

            if(!managerBO.isValidPassword()){
                throw new BusinessException("密码"+manager.getPassword()+"不足六位，无效");
            }

            int result=managerMapper.insert(manager);
            if (result > 0) {
                System.out.println("用户" + registerDTO.getUsername() + "注册成功");
                return true;
            } else {
                throw new BusinessException("用户" + registerDTO.getUsername() + "注册失败");
            }
        }catch(BusinessException e){
            throw e;
        } catch (Exception e) {
            System.out.println("注册异常：" + e.getMessage());
            throw new BusinessException("注册异常："+e.getMessage());
        }

    }

    //登录功能
    @Override
    public ManagerVO login(LoginDTO loginDTO) {
        try {
            if(loginDTO.getUsername()==null||loginDTO.getPassword()==null){
                throw new BusinessException("用户名或密码不能为空");
            }

            Manager manager = managerMapper.getByUsername(loginDTO.getUsername());

            if(manager==null){
                throw new BusinessException("用户不存在");
            }
            if(!manager.getPassword().equals(loginDTO.getPassword())){
                throw new BusinessException("密码错误");
            } else {
                System.out.println("用户"+loginDTO.getUsername()+"登录成功");
                //entity->VO
                ManagerVO managerVO=new ManagerVO();
                BeanUtils.copyProperties(manager,managerVO);
                managerVO.setPassword(null);//不返回密码
                return managerVO;
            }
        }catch (BusinessException e){
            throw e;
        }catch (Exception e) {
            System.out.println("登录异常：" + e.getMessage());
            throw new BusinessException("登录异常："+e.getMessage());
        }
    }


    //修改密码功能
    @Override
    public boolean changePassword(String userName, String oldPassword, String newPassword) {
        try {
            if(userName==null||oldPassword==null||newPassword==null){
                throw new BusinessException("用户名或密码不能为空");
            }
            Manager manager=managerMapper.getByUsername(userName);
            if(manager==null){
                throw new BusinessException("用户不存在");
            }
            if(!manager.getPassword().equals(oldPassword)){
                throw new BusinessException("密码错误");
            }
            if(manager.getPassword().equals(newPassword)){
                throw new BusinessException("新密码不能与旧密码相同");
            }
            manager.setPassword(newPassword);
            int result=managerMapper.update( manager);
            if (result > 0) {
                System.out.println("用户" + userName + "修改密码成功");
                return true;
            } else {
                throw new BusinessException("用户" + userName + "修改密码失败");
            }
        }catch (BusinessException e){
            throw e;
        }catch (Exception e) {
            System.out.println("修改密码异常：" + e.getMessage());
            throw new BusinessException("修改密码异常："+e.getMessage());
        }
    }


    //注销功能
    @Override
    public boolean logout(String userName, String password) {
        try {
            Manager manager=managerMapper.getByUsername(userName);
            if(manager==null){
                throw new BusinessException("用户不存在");
            }
            if(!manager.getPassword().equals(password)){
                throw new BusinessException("注销失败，密码错误");
            }
            int result=managerMapper.delete(manager.getId());
            if (result > 0) {
                System.out.println("用户" + userName + "注销成功");
                return true;
            }else{
                throw new BusinessException("用户" + userName + "注销失败");
            }
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("注销异常：" + e.getMessage());
            throw new BusinessException("注销异常："+e.getMessage());
        }
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
            throw new BusinessException("判断用户名是否存在异常："+e.getMessage());
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
            throw new BusinessException("判断用户是否已登录异常："+e.getMessage());
        }
    }

}
