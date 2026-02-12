package org.example.port_manage_system.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class ShipPageDTO {
    private List<ShipResponseDTO> list;
    private Integer total;
}
