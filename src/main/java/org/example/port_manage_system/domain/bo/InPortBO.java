package org.example.port_manage_system.domain.bo;

import lombok.Data;
import org.example.port_manage_system.domain.dto.InPortDTO;

@Data
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
}
