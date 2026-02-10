package org.example.port_manage_system.domain.bo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.port_manage_system.domain.dto.InPortDTO;

@Data
@NoArgsConstructor
public class InPortBO {
    private InPortDTO inPortDTO;
    public InPortBO(InPortDTO inPortDTO) {
        this.inPortDTO = inPortDTO;
    }
    //业务逻辑：检查入港申请状态
    public boolean isValidApplicationStatus(){
        return inPortDTO.getApplicationStatus().equals("SUBMITTED")||
                inPortDTO.getApplicationStatus().equals("DRAFT");
    }
    //业务逻辑：检查入港审核状态
    public boolean isValidReviewStatus(){
        return inPortDTO.getReviewStatus().equals("PENDING")||
                inPortDTO.getReviewStatus().equals("APPROVED")||
                inPortDTO.getReviewStatus().equals("REJECTED")||
                inPortDTO.getReviewStatus().isEmpty()||
                inPortDTO.getReviewStatus()==null;
    }

    //业务逻辑：检查入港审核状态
    public boolean isValidReviewStatusOnlyEnum(String status){
        return status.equals("PENDING")||
                status.equals("APPROVED")||
                status.equals("REJECTED");
    }

    //业务逻辑：检查船只入港申请是否已提交
    public boolean isSubmitted(String application){
        return "SUBMITTED".equals( application);
    }

    //业务逻辑：检查船只已离港
    public boolean isDeparted(InPortDTO inPortDTO){
        return inPortDTO.getExitTime()!=null;
    }
}
