package org.example.port_manage_system.domain.bo;

import lombok.Data;
import org.example.port_manage_system.domain.entity.ProductCategory;

@Data
public class CategoryBO {

    private ProductCategory productCategory;
    public CategoryBO(ProductCategory productCategory){
        this.productCategory=productCategory;
    }

    //业务逻辑：检查类别名称
    public boolean isValidCategoryName(){
        return productCategory.getCategoryName()!=null;
    }

    //业务逻辑：根据类别Id返回类别名称
    public String getCategoryNameById(Integer id){
        return productCategory.getCategoryName();
    }

    //业务逻辑：根据类别名称返回类别Id
    public Integer getCategoryIdByName(String name){
        return productCategory.getId();
    }
}
