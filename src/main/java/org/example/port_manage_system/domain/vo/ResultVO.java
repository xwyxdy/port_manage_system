package org.example.port_manage_system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVO<T> {

    private Integer code;
    private String message;
    private T data;

    // 简化创建方法 - 成功（带自定义消息）
    public static <T> ResultVO<T> success(String message, T data) {
        return new ResultVO<>(200, message, data);
    }

    // 简化创建方法 - 成功（默认消息）
    public static <T> ResultVO<T> success(T data) {
        return new ResultVO<>(200, "success", data);
    }

    // 简化创建方法 - 错误（默认400）
    public static <T> ResultVO<T> error(String message) {
        return new ResultVO<>(400, message, null);
    }

    // 简化创建方法 - 错误（自定义状态码）
    public static <T> ResultVO<T> error(Integer code, String message) {
        return new ResultVO<>(code, message, null);
    }

    // 常用错误状态快捷方法
    public static <T> ResultVO<T> notFound(String message) {
        return new ResultVO<>(404, message, null);
    }

    public static <T> ResultVO<T> serverError(String message) {
        return new ResultVO<>(500, message, null);
    }

    public static <T> ResultVO<T> forbidden(String message) {
        return new ResultVO<>(403, message, null);
    }

    public static <T> ResultVO<T> unauthorized(String message) {
        return new ResultVO<>(401, message, null);
    }
}