package org.example.port_manage_system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipVO {
    private Integer id;
    private String shipName;
    private Integer ownerId;
    private String shipType;
    private String shipSize;
    private String qualificationStatus;
}
