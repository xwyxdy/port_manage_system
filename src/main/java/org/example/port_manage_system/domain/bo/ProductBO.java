package org.example.port_manage_system.domain.bo;

import lombok.Data;
import org.example.port_manage_system.domain.entity.Product;

import java.math.BigDecimal;

@Data
public class ProductBO {

    private Product product;
    public ProductBO(Product product) {
        this.product = product;
    }

    //业务逻辑：检查商品价格是否合理
    public boolean isValidPrice(){
        return product.getPrice()!=null&&product.getPrice().compareTo(BigDecimal.ZERO)>0;
    }

    //业务逻辑：检查商品数量是否合理
    public boolean isValidQuantity(){
        return product.getQuantity()!=null&&product.getQuantity()>0;
    }

    //业务逻辑：增加商品数量
    public void addQuantity(Integer additionalQuantity){
        if(product.getQuantity()==null){
            product.setQuantity(additionalQuantity);
        }else{
            product.setQuantity(product.getQuantity()+additionalQuantity);
        }
    }

    //业务逻辑：减少商品数量
    public void subtractQuantity(Integer subtractQuantity){
        if(product.getQuantity()==null){
            product.setQuantity(-subtractQuantity);
        }else{
            product.setQuantity(product.getQuantity()-subtractQuantity);
        }
    }

    //业务逻辑：更新商品状态
    public void updateStatus(){
        if(product.getQuantity()!=null){
            product.setStatus("AVAILABLE");
        }else{
            product.setStatus("OUT_OF_STOCK");
        }
    }
}
