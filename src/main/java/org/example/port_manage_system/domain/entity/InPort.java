package org.example.port_manage_system.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InPort {
    private Integer id;
    private Integer shipId;
    private String shipName;
    private Timestamp entryTime;
    private Timestamp exitTime;
    private String applicationStatus;
    private String reviewStatus;
    private Integer reviewerId;
}
enum applicationStatus{
    SUBMITTED,    // 提交申请
    DRAFT,     // 草稿
}
enum reviewStatus{
    APPROVED,    // 审批通过
    REJECTED,// 审批拒绝
    PENDING
}

