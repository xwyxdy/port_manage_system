package org.example.port_manage_system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipCargoVO {

    private Integer id;
    private Integer shipId;
    private Integer productId;
    private Integer cargoQuantity;
    private String operationType;
}
