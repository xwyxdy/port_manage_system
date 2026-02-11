package org.example.port_manage_system.controller;

import org.example.port_manage_system.domain.vo.ProductCategoryListVO;
import org.example.port_manage_system.domain.vo.ResultVO;
import org.example.port_manage_system.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductCategoryController {

    @Autowired
    private ProductService productService;
    /**
     * 获取所有产品类别
     */
    @GetMapping("product-categories")
    public ResultVO<ProductCategoryListVO> getAllProductCategories() {
        try {
            ProductCategoryListVO categoryList = productService.getCategoryList();
            return ResultVO.success("查询成功", categoryList);
        } catch (Exception e) {
            return ResultVO.serverError("查询失败：" + e.getMessage());
        }
    }
}
