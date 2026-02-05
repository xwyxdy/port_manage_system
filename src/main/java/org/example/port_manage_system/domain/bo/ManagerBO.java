package org.example.port_manage_system.domain.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.port_manage_system.domain.entity.Manager;

@Data
@NoArgsConstructor
public class ManagerBO {

    private Manager manager;

    public ManagerBO(Manager manager) {
        this.manager = manager;
    }



    //业务逻辑：检查用户类型
    public boolean isValidUserType(){
        String userType=manager.getUserType();
        return userType.equals("PORT_ADMIN")||userType.equals("MARKET_ADMIN")||userType.equals("SHIP_OWNER");
    }

    //业务逻辑：检查密码规则
    public boolean isValidPassword(){
        String password=manager.getPassword();
        return password.length()>=6&&password.length()<=20&&password!=null;
    }

}
