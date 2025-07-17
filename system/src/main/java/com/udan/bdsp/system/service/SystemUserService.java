package com.udan.bdsp.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.udan.bdsp.system.entity.SystemUserEntity;
import com.udan.bdsp.system.vo.SystemUserItemVo;

import java.util.List;

/**
 * @Description 针对表【sys_user(员工信息表)】的数据库操作Service
 * @Author TOM FORD
 * @Date 2025-01-21 10:00:00
 */
public interface SystemUserService extends IService<SystemUserEntity> {


    /**
     * 根据ID查询用户基本信息及部门信息
     * @param id id
     * @return SystemUserItemVo
     */
    SystemUserItemVo getInfoWithDepById(Long id);



    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户信息
     */
    SystemUserEntity findByUsername(String username);

    /**
     * 根据用户ID查询用户角色
     * @param userId 用户ID
     * @return 角色编码列表
     */
    List<String> findRolesByUserId(Long userId);

    /**
     * 根据用户ID查询用户权限
     * @param userId 用户ID
     * @return 权限标识列表
     */
    List<String> findPermissionsByUserId(Long userId);

    /**
     * 创建用户
     * @param user 用户信息
     * @return 创建结果
     */
    boolean createUser(SystemUserEntity user);

    /**
     * 更新用户信息
     * @param user 用户信息
     * @return 更新结果
     */
    boolean updateUser(SystemUserEntity user);

    /**
     * 删除用户
     * @param userId 用户ID
     * @return 删除结果
     */
    boolean deleteUser(Long userId);

    /**
     * 批量删除用户
     * @param userIds 用户ID列表
     * @return 删除结果
     */
    boolean deleteUsers(List<Long> userIds);


    /**
     * 重置用户密码
     * @param userId 用户ID
     * @param newPassword 新密码
     * @return 重置结果
     */
    boolean resetPassword(Long userId, String newPassword);

    /**
     * 更新用户状态
     * @param userId 用户ID
     * @param status 状态
     * @return 更新结果
     */
    boolean updateUserStatus(Long userId, Integer status);
}