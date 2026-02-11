package org.example.port_manage_system.domain.vo;

import lombok.Data;

@Data
public class UserVO {
    private Integer id;
    private String username;
    private String userType; // SHIP_OWNER / PORT_ADMIN / MARKET_ADMIN
}