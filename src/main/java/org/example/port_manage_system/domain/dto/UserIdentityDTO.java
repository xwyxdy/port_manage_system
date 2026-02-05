package org.example.port_manage_system.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIdentityDTO {
    private String username;
    private String password;
    private String userType;
}
