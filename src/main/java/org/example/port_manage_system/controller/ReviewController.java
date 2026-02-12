package org.example.port_manage_system.controller;

import org.example.port_manage_system.domain.dto.ReviewQueryDTO;
import org.example.port_manage_system.domain.dto.ReviewResponseDTO;
import org.example.port_manage_system.domain.vo.ResultVO;
import org.example.port_manage_system.service.ReviewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController {

    @Autowired
    private ReviewerService reviewerService;
    /**
     * 审核
     */
    @PutMapping("api/port-entry-applications/{id}/review")
    public ResultVO<ReviewResponseDTO> update(@PathVariable Integer id, @RequestBody ReviewQueryDTO query){
        try {
            return reviewerService.reviewShip(id,query)?
                    ResultVO.success("审核成功",new ReviewResponseDTO(id,query.getReviewStatus(),query.getReviewerId()))
                    :ResultVO.error("审核失败");
        } catch (Exception e) {
            return ResultVO.error("审核异常"+e.getMessage());
        }
    }
}
