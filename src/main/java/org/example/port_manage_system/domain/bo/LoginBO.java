package org.example.port_manage_system.domain.bo;

import lombok.Data;

@Data
public class LoginBO {

    private String username;
    private String password;
    private String message;
    private boolean success;

    public LoginBO(String username,String password){
        this.username=username;
        this.password=password;
    }

    //业务逻辑：验证登录参数
    public void validate(){
        if(username==null||password==null){
            this.success=false;
            this.message="用户名或密码不能为空";
            return;
        }
        this.success=true;
        this.message="登录参数输入无误";

    }
}
