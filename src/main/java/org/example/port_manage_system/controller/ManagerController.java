package org.example.port_manage_system.controller;

import org.example.port_manage_system.pojo.Manager;
import org.example.port_manage_system.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/managers")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    //添加管理员信息
    @PostMapping("/add")
    public Map<String,Object> addManager(@RequestBody Manager manager){
        Map<String,Object> result =new HashMap<>();
        int rows=managerService.addManager(manager);
        try {
            if(rows>0){
                result.put("success",true);
                result.put("message","添加成功");
                result.put("data",manager);
            }else{
                result.put("success",false);
                result.put("massage","添加失败");
            }
        } catch (Exception e) {
            result.put("success",false);
            result.put("massage","添加失败"+e.getMessage());
        }
        return result;
    }


    //根据id查询管理员信息
    @GetMapping("/{id}")
    public Map<String,Object> getManagerById(@PathVariable Integer id){
        Map<String,Object> result =new HashMap<>();
        Manager manager=managerService.getManagerById(id);
        try {
            if(manager!=null){
                result.put("success",true);
                result.put("message","查询成功");
                result.put("data",manager);
            }else{
                result.put("success",false);
                result.put("massage","管理员不存在");
            }
        } catch (Exception e) {
            result.put("success",false);
            result.put("massage","查询失败"+e.getMessage());
        }
        return result;
    }

    //根据用户名查询管理员信息
    @GetMapping("/username/{username}")
    public Map<String,Object> getManagerByUsername(@PathVariable String username){
        Map<String,Object> result =new HashMap<>();
        Manager manager=managerService.getManagerByName(username);
        try {
            if(manager!=null){
                result.put("success",true);
                result.put("message","查询成功");
                result.put("data",manager);
            }else{
                result.put("success",false);
                result.put("massage","管理员不存在");
            }
        } catch (Exception e) {
            result.put("success",false);
            result.put("massage","查询失败"+e.getMessage());
        }
        return result;
    }

    //查询所有管理员信息
    @GetMapping("/all")
    public Map<String,Object> getAllManager(){
        Map<String,Object> result =new HashMap<>();
        List<Manager> managers= managerService.getManagers();
        try {
            if(managers!=null){
                result.put("success",true);
                result.put("message","查询成功");
                result.put("data",managers);
            }else{
                result.put("success",false);
                result.put("massage","暂无管理员数据");
            }
        } catch (Exception e) {
            result.put("success",false);
            result.put("massage","查询失败"+e.getMessage());
        }
        return result;
    }

    //根据管理员类型查询管理员信息
    @GetMapping("/type/{userType}")
    public Map<String,Object> getManagerByType(@PathVariable String userType){
        Map<String,Object> result =new HashMap<>();
        Manager manager=managerService.getManagerByType(userType);
        try {
            if(manager!=null){
                result.put("success",true);
                result.put("message","查询成功");
                result.put("data",manager);
            }else{
                result.put("success",false);
                result.put("massage","管理员不存在");
            }
        } catch (Exception e) {
            result.put("success",false);
            result.put("massage","查询失败"+e.getMessage());
        }
        return result;
    }

    //更新管理员信息
    @PutMapping("/update")
    public Map<String,Object> updateManager(@RequestBody Manager manager){
        Map<String,Object> result =new HashMap<>();
        int rows=managerService.updateManager(manager);
        try {
            if(rows>0){
                result.put("success",true);
                result.put("message","更新成功");
            }else{
                result.put("success",false);
                result.put("message","更新失败，管理员不存在");
            }
        } catch (Exception e) {
            result.put("success",false);
            result.put("message","更新失败"+e.getMessage());
        }
        return result;
    }

    //删除管理员
    @DeleteMapping("/delete/{id}")
    public Map<String,Object> deleteManager(@PathVariable Integer id){
        Map<String,Object> result =new HashMap<>();
        int rows=managerService.deleteManager(id);
        try {
            if(rows>0){
                result.put("success",true);
                result.put("message","删除成功");
            }else{
                result.put("success",false);
                result.put("message","删除失败，管理员不存在");
            }
        } catch (Exception e) {
            result.put("success",false);
            result.put("message","删除失败"+e.getMessage());
        }
        return result;
    }

    //获得所有姓张的
    @GetMapping("/allZhang")
    public Map<String,Object> getAllZhang(){
        Map<String,Object> result =new HashMap<>();
        List<Manager> managers=managerService.getAllZhang();
        try {
            if(managers!=null){
                result.put("success",true);
                result.put("message","查询成功");
                result.put("data",managers);
            }else{
                result.put("success",false);
                result.put("message","查询失败，没有姓张的人");
            }
        } catch (Exception e) {
            result.put("success",false);
            result.put("message","查询失败"+e.getMessage());
        }
        return result;
    }
}
