package com.udan.bdsp.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.udan.bdsp.system.entity.SystemUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 系统用户Mapper接口
 * @Author TOM FORD
 * @Date 2025-01-21 10:00:00
 */
@Mapper
public interface SystemUserMapper extends BaseMapper<SystemUser> {

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户信息
     */
    SystemUser findByUsername(@Param("username") String username);

    /**
     * 根据用户ID查询用户角色
     * @param userId 用户ID
     * @return 角色列表
     */
    List<String> findRolesByUserId(@Param("userId") Long userId);

    /**
     * 根据用户ID查询用户权限
     * @param userId 用户ID
     * @return 权限列表
     */
    List<String> findPermissionsByUserId(@Param("userId") Long userId);
} 