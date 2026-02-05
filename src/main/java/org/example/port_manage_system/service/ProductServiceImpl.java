package org.example.port_manage_system.service;

import org.example.port_manage_system.domain.bo.ProductBO;
import org.example.port_manage_system.domain.dto.ProductAddDTO;
import org.example.port_manage_system.domain.dto.ProductUpdateDTO;
import org.example.port_manage_system.domain.vo.ProductVO;
import org.example.port_manage_system.mapper.ProductMapper;
import org.example.port_manage_system.domain.entity.Product;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{

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
                System.out.println("价格必须大于0");
                return 0;
            }

            if(!productBO.isValidQuantity()){
                System.out.println("库存不能为负数");
                return 0;
            }

            int result=productMapper.insert(product);
            return result>0?1:0;
        } catch (BeansException e) {
            System.out.println("添加商品异常："+e.getMessage());
            return 0;
        }
    }

    @Override
    public int updateProduct(ProductUpdateDTO productUpdateDTO) {

        try {
            //先查询现有商品
            Product productExists=productMapper.getById(productUpdateDTO.getId());
            if(productExists==null){
                System.out.println("商品不存在");
                return 0;
            }

            //更新字段
            productExists.setQuantity(productUpdateDTO.getQuantity());
            productExists.setPrice(productUpdateDTO.getPrice());

            //使用BO处理业务逻辑
            ProductBO productBO=new ProductBO(productExists);
            productBO.updateStatus();//更新状态

            //检查
            if(!productBO.isValidPrice()){
                System.out.println("价格必须大于0");
                return 0;
            }
            if(!productBO.isValidQuantity()){
                System.out.println("库存数量不能为负");
                return 0;
            }

            //添加
            int result=productMapper.update(productExists);
            return result>0?1:0;
        } catch (Exception e) {
            System.out.println("更新商品异常："+e.getMessage());
            return 0;
        }

    }

    @Override
    public int deleteProduct(Integer id) {
        try {
            int result=productMapper.delete(id);
            return result>0?1:0;
        } catch (Exception e) {
            System.out.println("删除商品异常："+e.getMessage());
            return 0;
        }
    }

    @Override
    public ProductVO getProductById(Integer id) {
        try {
            Product product=productMapper.getById(id);
            if(product==null){
                return null;
            }
            return convertToVO(product);
        } catch (Exception e) {
            System.out.println("查询商品异常："+e.getMessage());
            return null;
        }
    }

    private ProductVO convertToVO(Product product) {
        if(product==null){
            return null;
        }
        ProductVO vo=new ProductVO();
        vo.setId(product.getId());
        vo.setProduct_name(product.getProduct_name());
        vo.setQuantity(product.getQuantity());
        vo.setPrice(product.getPrice());
        vo.setStatus(product.getStatus());
        vo.setCategory_id(product.getCategory_id());
        vo.setCreated_by(product.getCreated_by());
        if(product.getCategory_id()!=null){
            String categoryName=categoryService.getCategoryNameById(product.getCategory_id());
            vo.setCategory_name(categoryName);
        }
        return vo;
    }

    @Override
    public ProductVO getProductByName(String name) {
        try {
            Product product=productMapper.getByName(name);
            if(product==null){
                return null;
            }
            return convertToVO(product);
        } catch (Exception e) {
            System.out.println("查询商品异常："+e.getMessage());
            return null;
        }
    }

    @Override
    public List<ProductVO> getProductByCategoryId(Integer categoryId) {
        try {
            List<Product> products=productMapper.getByCategoryId(categoryId);
            return products.stream().map(this::convertToVO).collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("查询商品异常："+e.getMessage());
            return null;
        }
    }

    @Override
    public List<ProductVO> getProducts() {
        try {
            List<Product> products=productMapper.getAll();
            return products.stream().map(this::convertToVO).collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("查询商品异常："+e.getMessage());
            return null;
        }
    }

    @Override
    public List<ProductVO> getAllAvailable() {
        try {
            List<Product> products=productMapper.getAllAvailable();
            return products.stream().map(this::convertToVO).collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("查询商品异常："+e.getMessage());
            return null;
        }
    }

    @Override
    public boolean isProductExist(String productName) {
        try {
            if (productName == null || productName.trim().isEmpty()) {
                System.out.println("商品名称为空");
                return false;
            }

            // 调用Mapper查询商品
            Product product = productMapper.getByName(productName);
            return product != null;
        } catch (Exception e) {
            System.out.println("检查商品存在异常: " + e.getMessage());
            return false;
        }
    }
}
