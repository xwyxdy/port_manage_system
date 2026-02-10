package org.example.port_manage_system.service;

import org.example.port_manage_system.domain.bo.InPortBO;
import org.example.port_manage_system.domain.entity.Product;
import org.example.port_manage_system.domain.vo.InPortApplicationVO;
import org.example.port_manage_system.domain.vo.ProductVO;

import java.util.List;

public interface ReviewerService {

    //审核员手动提交审核意见
    boolean updateReviewStatus(String reviewStatus,Integer shipId);

    //审核员手动添加审核员id
    boolean updateReviewerId(Integer reviewerId,Integer shipId);

    //审核员在审核表中通过审核类型查找船只
    List<InPortApplicationVO> getByReview(String reviewStatus);

    //审核员可删除审核表中信息
    boolean deleteById(Integer id);

    //审核员查找船上所有货物
    List<ProductVO> getAllGoodsInCargo(Integer shipId);
}
