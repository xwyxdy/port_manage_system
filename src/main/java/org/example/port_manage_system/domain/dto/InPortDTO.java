package org.example.port_manage_system.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

//出港时间设置为null，
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InPortDTO {
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
