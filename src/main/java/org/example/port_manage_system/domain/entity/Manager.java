package org.example.port_manage_system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("users")
public class Manager {
    private String username;
    private String password;
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String phone;
    private String userType;
}

enum UserType{
    PORT_ADMIN,    // 港口管理员
    MARKET_ADMIN,  // 市场管理员
    SHIP_OWNER//船主
}