package org.example.port_manage_system.service;

import org.example.port_manage_system.pojo.Product;

import java.util.List;

public interface ProductService {

    //添加产品
    int addProduct(Product product);

    //更新产品
    int updateProduct(Product product);

    //删除产品
    int deleteProduct(Integer id);

    //根据id查询产品
    Product getProductById(Integer id);

    //根据产品名称查询产品
    Product getProductByName(String name);

    //根据产品类别id查询产品
    List< Product> getProductByCategoryId(Integer categoryId);

    //查询所有产品
    List<Product> getProducts();

    //查询所有状态为AVAILABLE的产品
    List<Product> getAllAvailable();


}
