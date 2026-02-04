package org.example.port_manage_system.controller;

import org.example.port_manage_system.pojo.Product;
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

    //添加商品
    @PostMapping("/add")
    public Map<String,Object> addProduct (@RequestBody Product product){
        Map<String,Object> result =new HashMap<>();
        int rows=productService.addProduct(product);
        try {
            if(rows>0){
                result.put("success",true);
                result.put("message","添加成功");
                result.put("data",product);
            }else{
                result.put("success",false);
                result.put("message","添加失败");
            }
        } catch (Exception e) {
            result.put("success",false);
            result.put("message","添加失败"+e.getMessage());
        }
        return result;
    }

    //查询所有商品
    @GetMapping("/all")
    public Map<String,Object> getAllProducts(){
        Map<String,Object> result =new HashMap<>();
        List<Product> products= productService.getProducts();
        try {
            if(products!=null){
                result.put("success",true);
                result.put("message","查询成功");
                result.put("data",products);
            }else{
                result.put("success",false);
                result.put("message","暂无数据");
            }
        } catch (Exception e) {
            result.put("success",false);
            result.put("message","查询失败"+e.getMessage());
        }
        return result;

    }

    //根据id查询商品
    @GetMapping("/{id}")
    public Map<String,Object> getProductById (@PathVariable Integer id){
        Map<String,Object> result =new HashMap<>();
        Product product=productService.getProductById(id);
        try {
            if(product!=null){
                result.put("success",true);
                result.put("message","查询成功");
                result.put("data",product);
            }else{
                result.put("success",false);
                result.put("message","商品不存在");
            }
        } catch (Exception e) {
            result.put("success",false);
            result.put("message","查询失败"+e.getMessage());
        }
        return result;
    }

    //根据商品名称查询商品
    @GetMapping("/name/{name}")
    public Map<String,Object> getProductByName (@PathVariable String name){
        Map<String,Object> result =new HashMap<>();
        Product product=productService.getProductByName(name);
        try {
            if(product!=null){
                result.put("success",true);
                result.put("message","查询成功");
                result.put("data",product);
            }else{
                result.put("success",false);
                result.put("message","商品不存在");
            }
        } catch (Exception e) {
            result.put("success",false);
            result.put("message","查询失败"+e.getMessage());
        }
        return result;
    }

    //根据商品类别id查询商品
    @GetMapping("/category/{category_id}")
    public Map<String,Object> getProductByCategory(@PathVariable Integer category_id){
        Map<String,Object> result =new HashMap<>();
        List<Product> products=productService.getProductByCategoryId(category_id);
        try {
            if(products!=null){
                result.put("success",true);
                result.put("message","查询成功");
                result.put("data",products);
            }else{
                result.put("success",false);
                result.put("message","商品不存在");
            }
        }catch (Exception e){
            result.put("success",false);
            result.put("message","查询失败"+e.getMessage());
        }
        return result;
    }

    //根据商品状态查询
    @GetMapping("/allAvailable")
    public Map<String,Object> getAllAvailableProducts(){
        Map<String,Object> result =new HashMap<>();
        List<Product> products=productService.getAllAvailable();
        try {
            if(products!=null){
                result.put("success",true);
                result.put("message","查询成功");
                result.put("data",products);
            }else{
                result.put("success",false);
                result.put("message","所有商品均已无存货");
            }
        }catch (Exception e){
            result.put("success",false);
            result.put("message","查询失败"+e.getMessage());
        }
        return result;
    }

    //更新商品
    @PutMapping("/update")
    public Map<String,Object> updateProduct (@RequestBody Product product){
        Map<String,Object> result =new HashMap<>();
        int rows=productService.updateProduct(product);
        try {
            if(rows>0){
                result.put("success",true);
                result.put("message","更新成功");
            }else{
                result.put("success",false);
                result.put("message","更新失败，商品不存在");
            }
        }catch (Exception e){
            result.put("success",false);
            result.put("message","更新失败"+e.getMessage());
        }
        return result;
    }

    //删除商品
    @DeleteMapping("/delete/{id}")
    public Map<String,Object> deleteManager(@PathVariable Integer id){
        Map<String,Object> result =new HashMap<>();
        int rows=productService.deleteProduct(id);
        try {
            if(rows>0){
                result.put("success",true);
                result.put("message","删除成功");
            }else{
                result.put("success",false);
                result.put("message","删除失败，商品不存在");
            }
        } catch (Exception e) {
            result.put("success",false);
            result.put("message","删除失败"+e.getMessage());
        }
        return result;
    }


}
