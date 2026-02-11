package org.example.port_manage_system.service.impl;

import org.example.port_manage_system.domain.bo.ProductBO;
import org.example.port_manage_system.domain.dto.ProductAddDTO;
import org.example.port_manage_system.domain.dto.ProductUpdateDTO;
import org.example.port_manage_system.domain.entity.Product;
import org.example.port_manage_system.domain.vo.ProductCategoryListVO;
import org.example.port_manage_system.domain.vo.ProductCategoryVO;
import org.example.port_manage_system.domain.vo.ProductVO;
import org.example.port_manage_system.exception.BusinessException;
import org.example.port_manage_system.mapper.ProductMapper;
import org.example.port_manage_system.service.CategoryService;
import org.example.port_manage_system.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryService categoryService;


    @Override
    public int addProduct(ProductAddDTO productAddDTO) {

        try {
            //DTO->entity
            Product product=new Product();
            BeanUtils.copyProperties(productAddDTO,product);

            //使用BO处理业务逻辑
            ProductBO productBO=new ProductBO(product);
            productBO.updateStatus();//根据数量设置状态


            //验证
            if(!productBO.isValidPrice()){
                throw new BusinessException("价格必须大于0");
            }

            if(!productBO.isValidQuantity()){
                throw new BusinessException("库存数量不能为负");
            }

            int result=productMapper.insert(product);
            return result>0?1:0;
        }catch (BusinessException e){
            throw e;
        }catch (Exception e) {
            throw new BusinessException("添加商品异常"+e.getMessage());
        }
    }

    @Override
    public int updateProduct(ProductUpdateDTO productUpdateDTO) {

        try {
            //先查询现有商品
            Product productExists=productMapper.getById(productUpdateDTO.getId());
            if(productExists==null){
                throw new BusinessException("商品不存在");
            }

            //更新字段
            productExists.setQuantity(productUpdateDTO.getQuantity());
            productExists.setUnitPrice(productUpdateDTO.getPrice());

            //使用BO处理业务逻辑
            ProductBO productBO=new ProductBO(productExists);
            productBO.updateStatus();//更新状态

            //检查
            if(!productBO.isValidPrice()){
                throw new BusinessException("价格必须大于0");
            }
            if(!productBO.isValidQuantity()){
                throw new BusinessException("库存数量不能为负");
            }

            //添加
            int result=productMapper.update(productExists);
            return result>0?1:0;
        }catch (BusinessException e){
            throw e;
        }catch (Exception e) {
            throw new BusinessException("更新商品异常"+e.getMessage());
        }

    }

    @Override
    public int deleteProduct(Integer id) {
        try {
            //检查是否有引用该商品的记录
            int count=productMapper.checkProductUsage(id);
            if(count>0){
                throw new BusinessException("商品正在被使用"); //表示商品正在被使用
            }
            int result=productMapper.delete(id);
            return result>0?1:0;
        }catch(BusinessException e){
            throw e;
        } catch (Exception e) {
            throw new BusinessException("删除商品异常"+e.getMessage());
        }
    }

    @Override
    public ProductVO getProductById(Integer id) {
        try {
            Product product=productMapper.getById(id);
            if(product==null){
                throw new BusinessException("商品不存在");
            }
            return convertToVO(product);
        }catch (BusinessException e){
            throw e;
        }catch (Exception e) {
            throw new BusinessException("查询商品异常"+e.getMessage());
        }
    }

    private ProductVO convertToVO(Product product) {
        if(product==null){
            return null;
        }
        ProductVO vo=new ProductVO();
        vo.setId(product.getId());
        vo.setProduct_name(product.getProductName());
        vo.setQuantity(product.getQuantity());
        vo.setPrice(product.getUnitPrice());
        vo.setStatus(product.getStatus());
        vo.setCategory_id(product.getCategoryId());
        vo.setCreated_by(product.getCreatedBy());
        if(product.getCategoryId()!=null){
            String categoryName=categoryService.getCategoryNameById(product.getCategoryId());
            vo.setCategory_name(categoryName);
        }
        return vo;
    }

    @Override
    public ProductVO getProductByName(String name) {
        try {
            Product product=productMapper.getByName(name);
            if(product==null){
                throw new BusinessException("商品不存在");
            }
            return convertToVO(product);
        }catch (BusinessException e){
            throw e;
        }catch (Exception e) {
            throw new BusinessException("查询商品异常"+e.getMessage());
        }
    }

    @Override
    public List<ProductVO> getProductByCategoryId(Integer categoryId) {
        try {
            List<Product> products=productMapper.getByCategoryId(categoryId);
            return products.stream().map(this::convertToVO).collect(Collectors.toList());
        }catch (BusinessException e){
            throw e;
        }catch (Exception e) {
            throw new BusinessException("查询商品异常"+e.getMessage());
        }
    }

    @Override
    public List<ProductVO> getProducts() {
        try {
            List<Product> products=productMapper.getAll();
            return products.stream().map(this::convertToVO).collect(Collectors.toList());
        }catch (BusinessException e){
            throw e;
        }catch (Exception e) {
            throw new BusinessException("查询商品异常"+e.getMessage());
        }
    }

    @Override
    public List<ProductVO> getAllAvailable() {
        try {
            List<Product> products=productMapper.getAllAvailable();
            return products.stream().map(this::convertToVO).collect(Collectors.toList());
        }catch (BusinessException e){
            throw e;
        }catch (Exception e) {
            throw new BusinessException("查询商品异常"+e.getMessage());
        }
    }

    @Override
    public boolean isProductExist(String productName) {
        try {
            if (productName == null || productName.trim().isEmpty()) {
                throw new BusinessException("商品名称不能为空");
            }

            // 调用Mapper查询商品
            Product product = productMapper.getByName(productName);
            return product != null;
        }catch (BusinessException e){
            throw e;
        }catch (Exception e) {
            throw new BusinessException("查询商品异常" + e.getMessage());
        }
    }

    @Override
    public ProductCategoryListVO getCategoryList() {
        List<ProductCategoryVO> categoryList = productMapper.getCategoryList();
        ProductCategoryListVO result=new ProductCategoryListVO();
        result.setList(categoryList);
        result.setTotal(categoryList.size());
        return result;
    }
}
