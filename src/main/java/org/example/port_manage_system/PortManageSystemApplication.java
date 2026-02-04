package org.example.port_manage_system;

import org.example.port_manage_system.mapper.ManagerMapper;
import org.example.port_manage_system.pojo.Manager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("org.example.port_manage_system.mapper")
public class PortManageSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortManageSystemApplication.class, args);
        System.out.println("港口管理系统启动成功！");
        System.out.println("访问地址：http://localhost:8081");
        System.out.println("API文档：");
        System.out.println("  GET  /api/managers/all         - 查询所有管理员");
        System.out.println("  GET  /api/managers/{id}        - 根据ID查询");
        System.out.println("  POST /api/managers/add         - 添加管理员");
        System.out.println("  PUT  /api/managers/update      - 更新管理员");
        System.out.println("  DELETE /api/managers/delete/{id} - 删除管理员");
    }
//    @Bean
//    public CommandLineRunner init(ManagerMapper managerMapper) {
//        return args -> {
//            System.out.println(managerMapper.getById(1));
//        };
//    }
}
