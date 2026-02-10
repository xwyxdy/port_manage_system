package org.example.port_manage_system.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargoShip {
    private Integer id;
    private Integer shipId;
    private Integer productId;
    private Integer cargoQuantity;
}
