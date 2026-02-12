package org.example.port_manage_system.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipResponseDTO {
    private Integer id;
    private String shipName;
    private Integer ownerId;
    private String shipType;
    private String shipSize;
    private String qualificationStatus;
    private String ownerName;
}
enum ShipType{
    CARGO,    // 货船
    FISHING,     // 渔船
    CRUISE    // 游船
}
enum shipSize{
    SMALL,    // 小型船
    MEDIUM,     // 中型船
    LARGE    // 大型船
}
enum qualificationStatus{
    APPROVED,    // 满足要求
    REJECTED,
    PENDING
}
