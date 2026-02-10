package org.example.port_manage_system.exception;

public class BusinessException extends RuntimeException{
    private String message;

    public BusinessException(String message){
        super(message);
        this.message=message;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
