package org.example.port_manage_system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InPortApplicationVO {

    private Integer id;            // 申请ID
    private Integer shipId;        // 船只ID
    private String shipName;       // 船只名称
    private Timestamp entryTime;   // 申请入港时间
    private Timestamp exitTime;    // 计划出港时间
    private String applicationStatus;  // 申请状态
    private String reviewStatus;       // 审批状态
    private Integer reviewerId;        // 审批人ID
}
