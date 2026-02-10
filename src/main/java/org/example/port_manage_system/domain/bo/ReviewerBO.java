package org.example.port_manage_system.domain.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.port_manage_system.domain.entity.Manager;

@Data
@NoArgsConstructor
public class ReviewerBO {
    public boolean isPortAdmin(String userType){
        return userType.equals("PORT_ADMIN");
    }
}
