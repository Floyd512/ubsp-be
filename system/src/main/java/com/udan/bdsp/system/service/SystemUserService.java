package com.udan.bdsp.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.udan.bdsp.system.entity.SystemUser;

import java.util.List;

/**
 * @Description 系统用户服务接口
 * @Author TOM FORD
 * @Date 2025-01-21 10:00:00
 */
public interface SystemUserService extends IService<SystemUser> {

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户信息
     */
    SystemUser findByUsername(String username);

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
    boolean createUser(SystemUser user);

    /**
     * 更新用户信息
     * @param user 用户信息
     * @return 更新结果
     */
    boolean updateUser(SystemUser user);

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
     * 分配用户角色
     * @param userId 用户ID
     * @param roleIds 角色ID列表
     * @return 分配结果
     */
    boolean assignUserRoles(Long userId, List<Long> roleIds);

    /**
     * 根据用户ID查询角色ID列表
     * @param userId 用户ID
     * @return 角色ID列表
     */
    List<Long> findRoleIdsByUserId(Long userId);

    /**
     * 检查用户名是否存在
     * @param username 用户名
     * @param excludeId 排除的用户ID
     * @return 是否存在
     */
    boolean checkUsernameExists(String username, Long excludeId);

    /**
     * 检查邮箱是否存在
     * @param email 邮箱
     * @param excludeId 排除的用户ID
     * @return 是否存在
     */
    boolean checkEmailExists(String email, Long excludeId);

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