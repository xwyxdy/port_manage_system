package org.example.port_manage_system.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDTO {

    private String username;
    private String oldPassword;
    private String newPassword;
}
