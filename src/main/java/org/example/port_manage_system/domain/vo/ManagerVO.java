package org.example.port_manage_system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerVO {

    private Integer id;
    private String username;
    private String password;
    private String phone;
    private String userType;
}
