package com.udan.ubsp.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.udan.ubsp.system.entity.SystemUserEntity;
import com.udan.ubsp.system.vo.SystemUserItemVo;

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
     * @param id 用户ID
     * @return 用户信息
     */
    SystemUserEntity getById(Long id);
}