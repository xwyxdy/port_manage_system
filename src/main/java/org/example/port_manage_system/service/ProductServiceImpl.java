package org.example.port_manage_system.service;

import org.example.port_manage_system.mapper.ProductMapper;
import org.example.port_manage_system.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductMapper productMapper;

    @Override
    public int addProduct(Product product) {
        return productMapper.insert(product)>0?1:0;
    }

    @Override
    public int updateProduct(Product product) {
        return productMapper.update(product)>0?1:0;
    }

    @Override
    public int deleteProduct(Integer id) {
        return productMapper.delete(id)>0?1:0;
    }

    @Override
    public Product getProductById(Integer id) {
        return productMapper.getById(id);
    }

    @Override
    public Product getProductByName(String name) {
        return productMapper.getByName(name);
    }

    @Override
    public List<Product> getProductByCategoryId(Integer categoryId) {
        return productMapper.getByCategoryId(categoryId);
    }

    @Override
    public List<Product> getProducts() {
        return productMapper.getAll();
    }

    @Override
    public List<Product> getAllAvailable() {
        return productMapper.getAllAvailable();
    }
}
