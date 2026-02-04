package org.example.port_manage_system;

import org.example.port_manage_system.mapper.ManagerMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PortManageSystemApplicationTests {

    @Autowired
    private ManagerMapper managerMapper;
    @Test
    void contextLoads() {
    }
    @Test
    void testGetById() {
        System.out.println(managerMapper.getById(1));
    }
}
