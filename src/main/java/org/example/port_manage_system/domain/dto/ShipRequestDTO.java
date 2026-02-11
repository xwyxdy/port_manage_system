package org.example.port_manage_system.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipRequestDTO {
    private String shipName;
    private Integer ownerId;
    private String shipType;
    private String shipSize;
}
