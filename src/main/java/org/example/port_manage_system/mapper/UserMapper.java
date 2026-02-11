package org.example.port_manage_system.mapper;

import org.apache.ibatis.annotations.*;
import org.example.port_manage_system.domain.entity.Manager;
import org.example.port_manage_system.domain.vo.UserVO;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 根据用户类型查询用户列表
     * 只返回启用的用户（status=1），只返回id, username, user_type字段
     * @param userType 用户类型：PORT_ADMIN / MARKET_ADMIN / SHIP_OWNER
     * @return 用户列表
     */
    @Select("SELECT id, username, user_type as userType FROM users WHERE user_type = #{userType} ORDER BY id ASC")
    List<UserVO> selectByUserType(@Param("userType") String userType);

    /**
     * 根据用户类型查询用户数量
     * @param userType 用户类型
     * @return 用户数量
     */
    @Select("SELECT COUNT(*) FROM users WHERE user_type = #{userType}")
    Integer countByUserType(@Param("userType") String userType);

    /**
     * 根据ID查询用户
     * 返回所有字段
     * @param id 用户ID
     * @return 用户信息
     */
    @Select("SELECT id, username, user_type as userType, phone FROM users WHERE id = #{id}")
    UserVO selectById(@Param("id") Integer id);

    /**
     * 检查用户名是否存在
     * @param username 用户名
     * @return 存在数量
     */
    @Select("SELECT COUNT(*) FROM users WHERE username = #{username}")
    Integer checkUsernameExists(@Param("username") String username);

    /**
     * 根据用户名和密码查询用户（用于登录）
     * @param username 用户名
     * @param password 密码
     * @return 用户信息
     */
    @Select("SELECT id, username, user_type as userType, phone FROM users WHERE username = #{username} AND password = #{password}")
    UserVO selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    /**
     * 插入新用户（用于注册）
     * @param user 用户实体
     * @return 影响的行数
     */
    @Insert("INSERT INTO users (username, password, phone, user_type) " +
            "VALUES (#{username}, #{password}, #{phone}, #{userType})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Manager user);

    /**
     * 更新用户信息
     * @param user 用户实体
     * @return 影响的行数
     */
    @Update("UPDATE users SET " +
            "username = #{username}, " +
            "phone = #{phone}, " +
            "user_type = #{userType} " +
            "WHERE id = #{id}")
    int update(Manager user);

    /**
     * 更新用户密码
     * @param id 用户ID
     * @param newPassword 新密码
     * @return 影响的行数
     */
    @Update("UPDATE users SET password = #{newPassword} WHERE id = #{id}")
    int updatePassword(@Param("id") Integer id, @Param("newPassword") String newPassword);

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户信息
     */
    @Select("SELECT id, username, password, user_type as userType, phone FROM users WHERE username = #{username}")
    UserVO selectByUsername(@Param("username") String username);

    /**
     * 查询所有用户（用于管理）
     * @return 所有用户列表
     */
    @Select("SELECT id, username, user_type as userType, phone FROM users ORDER BY id DESC")
    List<UserVO> selectAll();

    /**
     * 根据用户类型查询
     * @param userType 用户类型
     * @return 用户列表
     */
    @Select("SELECT id, username, user_type as userType, phone FROM users WHERE user_type = #{userType} ORDER BY id DESC")
    List<UserVO> selectByUserTypeOnly(@Param("userType") String userType);

    /**
     * 删除用户
     * @param id 用户ID
     * @return 影响的行数
     */
    @Delete("DELETE FROM users WHERE id = #{id}")
    int deleteById(@Param("id") Integer id);

    /**
     * 检查用户类型是否匹配
     * @param id 用户ID
     * @param userType 用户类型
     * @return 匹配的数量
     */
    @Select("SELECT COUNT(*) FROM users WHERE id = #{id} AND user_type = #{userType}")
    Integer checkUserType(@Param("id") Integer id, @Param("userType") String userType);
}