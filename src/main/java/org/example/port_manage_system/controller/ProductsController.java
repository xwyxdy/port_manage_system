package org.example.port_manage_system.controller;

import org.example.port_manage_system.domain.bo.ProductBO;
import org.example.port_manage_system.domain.dto.ProductAddDTO;
import org.example.port_manage_system.domain.dto.ProductPageDTO;
import org.example.port_manage_system.domain.dto.ProductQueryDTO;
import org.example.port_manage_system.domain.dto.ProductUpdateDTO;
import org.example.port_manage_system.domain.vo.ApiResultVO;
import org.example.port_manage_system.domain.vo.ProductVO;
import org.example.port_manage_system.domain.vo.ResultVO;
import org.example.port_manage_system.service.CategoryService;
import org.example.port_manage_system.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

//    //添加商品
//    @PostMapping("/add")
//    public ApiResultVO<ProductVO> addProduct(@RequestBody ProductAddDTO productAddDTO) {
//        try {
//            // 检查商品是否存在
//            if(productService.isProductExist(productAddDTO.getProductName())) {
//                // 商品已存在，应该更新数量，而不是新增
//                ProductVO existingProduct = productService.getProductByName(productAddDTO.getProductName());
//                if (existingProduct != null) {
//                    // 创建更新DTO，增加数量
//                    ProductUpdateDTO updateDTO = new ProductUpdateDTO();
//                    updateDTO.setId(existingProduct.getId());
//                    updateDTO.setQuantity(existingProduct.getQuantity() + productAddDTO.getQuantity());  // 增加数量
//                    updateDTO.setPrice(productAddDTO.getPrice());  // 可以更新价格
//
//                    int updateResult = productService.updateProduct(updateDTO);
//                    if (updateResult > 0) {
//                        ProductVO updated = productService.getProductById(existingProduct.getId());
//                        return ApiResultVO.success("商品已存在，已增加商品数量", updated);
//                    }
//                }
//                return ApiResultVO.error("更新现有商品失败");
//            } else {
//                // 商品不存在，新增
//                int result = productService.addProduct(productAddDTO);
//                if(result > 0) {
//                    ProductVO productVO = productService.getProductByName(productAddDTO.getProductName());
//                    return ApiResultVO.success("添加成功", productVO);
//                } else {
//                    return ApiResultVO.error("添加失败");
//                }
//            }
//        } catch (Exception e) {
//            return ApiResultVO.error("添加异常" + e.getMessage());
//        }
//    }
//
//    //查询所有商品
//    @GetMapping("/all")
//    public ApiResultVO<List<ProductVO>> getAllProducts() {
//        try {
//            List<ProductVO> products = productService.getProducts();
//            if (products != null && !products.isEmpty()) {
//                // 直接返回List，不需要强制转换
//                return ApiResultVO.success("查询成功", products);
//            } else {
//                return ApiResultVO.error("暂无数据");
//            }
//        } catch (Exception e) {
//            return ApiResultVO.error("查询失败: " + e.getMessage());
//        }
//    }
//
//    //根据id查询商品
//    @GetMapping("/id")
//    public ApiResultVO<ProductVO> getProductById (@RequestParam Integer id){
//        try {
//            ProductVO productVO = productService.getProductById(id);
//            if (productVO != null) {
//                return ApiResultVO.success("查询成功", productVO);
//            } else {
//                return ApiResultVO.error("商品不存在");
//            }
//        } catch (Exception e) {
//            return ApiResultVO.error("查询失败: " + e.getMessage());
//        }
//    }
//
//    //根据商品名称查询商品
//    @GetMapping("/name")
//    public ApiResultVO<ProductVO> getProductByName (@RequestParam String name){
//        try {
//            ProductVO productVO = productService.getProductByName(name);
//            if (productVO != null) {
//                return ApiResultVO.success("查询成功", productVO);
//            } else {
//                return ApiResultVO.error("商品不存在");
//            }
//        } catch (Exception e) {
//            return ApiResultVO.error("查询失败: " + e.getMessage());
//        }
//    }

    /**
     * 根据商品类别分页查询
     * @return
     */
    @GetMapping
    public ResultVO<ProductPageDTO> getProductByCategory(@RequestParam(defaultValue = "1") Integer page,
                                               @RequestParam(defaultValue = "10")Integer pageSize,
                                               @RequestParam(required = false) Integer categoryId) {
        try {
            ProductQueryDTO query = new ProductQueryDTO();
            query.setPage(page);
            query.setPageSize(pageSize);
            query.setCategoryId(categoryId);
            ProductPageDTO products = productService.getProductByCategoryId(query);
            return ResultVO.success("查询成功", products);
        } catch (Exception e) {
            return ResultVO.serverError("查询失败：" + e.getMessage());
        }

    }

//    //根据商品状态查询
//    @GetMapping("/allAvailable")
//    public ApiResultVO<List<ProductVO>> getAllAvailableProducts() {
//        try {
//            List<ProductVO> products = productService.getAllAvailable();
//            if (products != null && !products.isEmpty()) {
//                return ApiResultVO.success("查询成功", products);  //直接返回List
//            } else {
//                return ApiResultVO.error("所有商品均已无存货");
//            }
//        } catch (Exception e) {
//            return ApiResultVO.error("查询失败: " + e.getMessage());
//        }
//    }
//
//    //更新商品
//    @PutMapping("/update")
//    public ApiResultVO<ProductVO> updateProduct (@RequestBody ProductUpdateDTO productUpdateDTO){
//        try {
//            int result = productService.updateProduct(productUpdateDTO);
//            if (result > 0) {
//                return ApiResultVO.success("更新成功", null);
//            } else {
//                return ApiResultVO.error("更新失败，商品不存在");
//            }
//        } catch (Exception e) {
//            return ApiResultVO.error("更新失败: " + e.getMessage());
//        }
//    }
//
//    //删除商品
//    @DeleteMapping("/delete/id")
//    public ApiResultVO<ProductVO> deleteManager(@RequestParam Integer id){
//        try {
//            int result = productService.deleteProduct(id);
//            if (result > 0) {
//                return ApiResultVO.success("删除成功", null);
//            } else {
//                return ApiResultVO.error("删除失败，商品正在被使用，无法删除");
//            }
//        } catch (Exception e) {
//            return ApiResultVO.error("删除失败: " + e.getMessage());
//        }
//    }


}
