package org.example.port_manage_system.service;

import org.example.port_manage_system.mapper.ManagerMapper;
import org.example.port_manage_system.pojo.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AuthServiceImpl implements AuthService{

    @Autowired
    private ManagerMapper managerMapper;

    @Override
    public boolean register(Manager manager) {
        //检查用户名是否已经存在
        try {
            if(isUserNameExist(manager.getUsername())){
                System.out.println("用户名"+manager.getUsername()+"已存在");
                return false;
            }

            int result = managerMapper.insert(manager);
            if(result>0){
                System.out.println("用户"+manager.getUsername()+"注册成功");
                return true;
            }else{
                System.out.println("用户"+manager.getUsername()+"注册失败");
                return false;
            }
        } catch (Exception e) {
            System.out.println("注册异常："+e.getMessage());
            return false;
        }
    }

    @Override
    public Manager login(String username, String password) {
        try {
            if(username==null || password==null){
                System.out.println("登录失败：用户名和密码不能为空");
                return null;
            }
            Manager manager = managerMapper.getByUsername(username);
            if(manager==null){
                System.out.println("登录失败：用户名不存在");
                return null;
            }
            if(manager.getPassword().equals(password)){
                System.out.println("登录成功：用户"+username+"登录成功");
                return manager;
            }else{
                System.out.println("登录失败：密码错误");
                return null;
            }
        } catch (Exception e) {
            System.out.println("登录异常："+e.getMessage());
            return null;
        }
    }

    @Override
    public boolean changePassword(String userName, String oldPassword, String newPassword) {
        try {
            if(userName==null || oldPassword==null || newPassword==null){
                System.out.println("修改密码失败：用户名和密码不能为空");
                return false;
            }
            Manager manager = managerMapper.getByUsername(userName);
            if(manager==null){
                System.out.println("修改密码失败：用户名不存在");
                return false;
            }
            if(oldPassword.equals(newPassword)){
                System.out.println("修改密码失败：新密码不能与旧密码相同");
                return false;
            }
            manager.setPassword(newPassword);
            int result = managerMapper.update(manager);
            if(result>0){
                System.out.println("修改密码成功");
                return true;
            }else{
                System.out.println("修改密码失败");
                return false;
            }
        } catch (Exception e) {
            System.out.println("修改密码异常："+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean logout(String userName, String password) {
        Manager manager = managerMapper.getByUsername(userName);
        if(manager==null){
            System.out.println("注销失败：用户名不存在");
            return false;
        }
        if(!manager.getPassword().equals(password)){
            System.out.println("注销失败：密码错误");
            return false;
        }
        managerMapper.delete(manager.getId());
        System.out.println("注销成功");
        return true;
    }

    @Override
    public boolean isUserNameExist(String username) {
        try {
            Manager manager = managerMapper.getByUsername(username);
            System.out.println("用户"+username+"是否存在："+(manager!=null));
            return manager!=null;
        } catch (Exception e) {
            System.out.println("查询用户名异常："+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean isUserLoggedInAs(String username, String password, String requiredUserType) {
        try {
            Manager manager = managerMapper.getByUsername(username);
            System.out.println("用户"+username+"是否以"+requiredUserType+"身份登录："+(manager!=null && manager.getPassword().equals(password) && manager.getUserType().equals(requiredUserType)));
            return manager!=null && manager.getPassword().equals(password) && manager.getUserType().equals(requiredUserType);
        } catch (Exception e) {
            System.out.println("查询用户登录状态异常："+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean isUserTypeValid(String userType) {
        if(userType==null){
            System.out.println("用户类型无效：用户类型不能为空");
            return false;
        }
        return userType.equals("PORT_ADMIN") || userType.equals("MARKET_ADMIN") || userType.equals("SHIP_OWNER");
    }
}
