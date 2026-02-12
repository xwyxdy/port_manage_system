package org.example.port_manage_system.service;

import org.example.port_manage_system.domain.dto.ProductAddDTO;
import org.example.port_manage_system.domain.dto.ProductPageDTO;
import org.example.port_manage_system.domain.dto.ProductQueryDTO;
import org.example.port_manage_system.domain.dto.ProductUpdateDTO;
import org.example.port_manage_system.domain.entity.Product;
import org.example.port_manage_system.domain.vo.ProductCategoryListVO;
import org.example.port_manage_system.domain.vo.ProductVO;

import java.util.List;

public interface ProductService {

    //添加产品
    int addProduct(ProductAddDTO productAddDTO);

    //更新产品
    int updateProduct(ProductUpdateDTO productUpdateDTO);

    //删除产品
    int deleteProduct(Integer id);

    //根据id查询产品
    ProductVO getProductById(Integer id);

    //根据产品名称查询产品
    ProductVO getProductByName(String name);


    /**
     * 根据产品类别id查询产品
     * @param
     * @return
     */
    ProductPageDTO getProductByCategoryId(ProductQueryDTO query);

    //查询指定类别的产品
    List<Product> getProductsByCategoryId(Integer categoryId);

    //查询所有产品
    List<ProductVO> getProducts();

    //查询所有状态为AVAILABLE的产品
    List<ProductVO> getAllAvailable();


    boolean isProductExist(String productName);

    /**
     * 获取所有产品类别
     * @return
     */
    ProductCategoryListVO getCategoryList();
}
