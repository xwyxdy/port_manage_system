package org.example.port_manage_system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.example.port_manage_system.domain.entity.Manager;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
public interface ManagerMapper {

    //查询所有用户
    @Select("select * from users")
    List<Manager> getAll();

    //根据id查询用户
    @Select("select * from users where id = #{id}")
    Manager getById(Integer id);

    //根据用户名查询用户
    @Select("select * from users where username=#{username}")
    Manager getByUsername(String username);

    //根据用户类型查询用户
    @Select("select * from users where user_type=#{userType}")
    Manager getByUserType(String userType);

    //插入用户，返回自增id
    @Insert("insert into users(username,password,phone,user_type)"
            +"values(#{username},#{password},#{phone},#{userType})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insert(Manager manager);

    //根据id删除用户
    @Delete("delete from users where id = #{id}")
    int delete(Integer id);

    //更新用户信息
    @Update("update users set username=#{username},password=#{password},"
            +"phone=#{phone},user_type=#{userType} where id=#{id}")
    int update(Manager manager);

    //查询所有姓张的用户
    @Select("select * from users where username like '张%'")
    List<Manager> getAllZhang();


}