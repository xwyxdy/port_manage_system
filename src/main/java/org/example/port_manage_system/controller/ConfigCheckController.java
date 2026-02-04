package org.example.port_manage_system.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigCheckController {

    @Value("${server.port:8081}")
    private String port;

    @GetMapping("/config")
    public String checkConfig() {
        return "当前端口配置: " + port;
    }
}
