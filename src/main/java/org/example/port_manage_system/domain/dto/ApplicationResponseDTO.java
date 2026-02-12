package org.example.port_manage_system.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponseDTO {

    private Integer id;
    private Integer shipId;
    private String shipName;
    private Timestamp entryTime;
    private Timestamp exitTime;
    private String applicationStatus;
    private String reviewStatus;
    private Integer reviewerId;
    private String reviewerName;
}