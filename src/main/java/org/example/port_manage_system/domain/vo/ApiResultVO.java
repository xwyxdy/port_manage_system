package org.example.port_manage_system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResultVO< T> {

    private boolean success;
    private String message;
    private T data;
    private Date timeStamp;
    private String note;

//    private ApiResultVO(boolean success, String message, T data, Date timeStamp, String note){
//        this.success=success;
//        this.message=message;
//        this.data=data;
//        this.timeStamp=timeStamp;
//        this.note=note;
//    }

    //简化创建方法
    public static <T> ApiResultVO<T> success(String message,T data){
        return new ApiResultVO<>(true,message,data,new Date(),null);
    }

    public static <T>ApiResultVO<T> error(String message){
        return new ApiResultVO<>(false,message,null,new Date(),null);
    }



}
