package org.example.port_manage_system.controller;

import org.example.port_manage_system.domain.vo.ApiResultVO;
import org.example.port_manage_system.domain.vo.InPortApplicationVO;
import org.example.port_manage_system.domain.vo.ProductVO;
import org.example.port_manage_system.exception.BusinessException;
import org.example.port_manage_system.service.ReviewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/review")
public class ReviewController {

    @Autowired
    private ReviewerService reviewerService;

    @PutMapping("review")
    public ApiResultVO<InPortApplicationVO> updateReview(@RequestParam String reviewStatus, @RequestParam Integer shipId){
        try {
            boolean success=reviewerService.updateReviewStatus(reviewStatus,shipId);
            if(success){
                System.out.println("审核意见提交成功");
                return ApiResultVO.success("审核意见提交成功",null);
            }else{
                return ApiResultVO.error("审核意见提交失败");
            }
        } catch (Exception e) {
            System.out.println("审核意见提交异常"+e.getMessage());
            return ApiResultVO.error("审核意见提交异常"+e.getMessage());
        }
    }

    @PutMapping("reviewerId")
    public ApiResultVO<InPortApplicationVO> updateReviewerId(@RequestParam Integer reviewerId, @RequestParam Integer shipId){
        try {
            boolean success=reviewerService.updateReviewerId(reviewerId,shipId);
            if(success){
                System.out.println("审核员id提交成功");
                return ApiResultVO.success("审核员id提交成功",null);
            }else{
                return ApiResultVO.error("审核员id提交失败");
            }
        } catch (Exception e) {
            System.out.println("审核员id提交异常"+e.getMessage());
            return ApiResultVO.error("审核员id提交异常"+e.getMessage());
        }
    }

    @GetMapping("getByReview")
    public ApiResultVO<List<InPortApplicationVO>> getByReview(@RequestParam String reviewStatus){
        try {
            List<InPortApplicationVO> result= reviewerService.getByReview(reviewStatus);
            if(result!=null){
                System.out.println("查询成功");
                return ApiResultVO.success("查询成功",result);
            }else{
                return ApiResultVO.error("查询失败");
            }
        } catch (Exception e) {
            System.out.println("查询异常");
            return ApiResultVO.error("查询异常"+e.getMessage());
        }
    }

    @DeleteMapping("deleteById")
    public ApiResultVO<InPortApplicationVO> deleteById(@RequestParam Integer id){
        try {
            boolean success=reviewerService.deleteById(id);
            if(success){
                System.out.println("删除成功");
                return ApiResultVO.success("删除成功",null);
            }else{
                return ApiResultVO.error("删除失败");
            }
        } catch (Exception e) {
            System.out.println("删除异常"+e.getMessage());
            return ApiResultVO.error("删除异常"+e.getMessage());
        }
    }

    @GetMapping("getAllGoodsInCargo")
    public ApiResultVO<List<ProductVO>> getAllGoodsInCargo(@RequestParam Integer shipId){
        try {
            List<ProductVO> result= reviewerService.getAllGoodsInCargo(shipId);
            if(result!=null){
                System.out.println("查询成功");
                return ApiResultVO.success("查询成功",result);
            }else{
                return ApiResultVO.error("查询失败");
            }
        } catch (Exception e) {
            System.out.println("查询异常");
            return ApiResultVO.error("查询异常"+e.getMessage());
        }
    }
}
