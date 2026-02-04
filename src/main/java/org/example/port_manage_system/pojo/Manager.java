package org.example.port_manage_system.pojo;

import lombok.Data;

@Data
public class Manager {
    private String username;
    private String password;
    private Integer id;
    private String phone;
    private String userType;
}

enum UserType{
    PORT_ADMIN,    // 港口管理员
    MARKET_ADMIN,  // 市场管理员
    SHIP_OWNER//船主
}