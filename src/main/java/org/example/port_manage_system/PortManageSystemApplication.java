package org.example.port_manage_system;


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
//        System.out.println("管理员增删改查API文档：");
//        System.out.println("  GET  /api/managers/all         - 查询所有管理员");
//        System.out.println("  GET  /api/managers/{id}        - 根据ID查询");
//        System.out.println("  POST /api/managers/add         - 添加管理员");
//        System.out.println("  PUT  /api/managers/update      - 更新管理员");
//        System.out.println("  DELETE /api/managers/delete/{id} - 删除管理员");
//        System.out.println("商品增删改查接口文档：");
//        System.out.println("  POST /api/products/add          -添加商品");
//        System.out.println("  PUT  /api/products/update       -更新商品");
//        System.out.println("  DELETE /api/products/delete/{id} - 删除商品");
//        System.out.println("  GET  /api/products/all         -查询所有商品");
//        System.out.println("  GET  /api/products/{id}         -根据ID查询商品");
//        System.out.println("  GET  /api/products/name/{name}   -根据商品名称查询商品");
//        System.out.println("  GET  /api/products/category/{category_id}  -根据商品类型查询商品");
//        System.out.println("  GET  /api/products/allAvailable  -查询所有有库存的商品");
//        System.out.println("用户登录，注册，注销API文档：");
//        System.out.println("  POST /api/auth/login           -用户登录");
//        System.out.println("  POST /api/auth/register        -用户注册");
//        System.out.println("  POST /api/auth/logout          -用户注销");
//        System.out.println("  POST api/auth/changePassword/{changePasswordData}  -用户修改密码");
//        System.out.println("  POST /api/auth/isUserNameExist/{username}  -判断用户名是否存在");
//        System.out.println("  POST /api/auth/isUserLoggedInAs/{loginData}  -判断用户是否以特定身份登录");
    }
//    @Bean
//    public CommandLineRunner init(ManagerMapper managerMapper) {
//        return args -> {
//            System.out.println(managerMapper.getById(1));
//        };
//    }
}
