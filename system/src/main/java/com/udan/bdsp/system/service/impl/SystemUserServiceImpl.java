package com.udan.bdsp.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.udan.bdsp.system.entity.SystemUserEntity;
import com.udan.bdsp.system.mapper.SystemUserMapper;
import com.udan.bdsp.system.service.SystemUserService;

import com.udan.bdsp.system.vo.SystemUserItemVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Description 针对表【system_user(员工信息表)】的数据库操作Service实现
 * @Author TOM FORD
 * @Date 2025-07-12 17:07:15
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUserEntity> implements SystemUserService {

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Override
    public SystemUserItemVo getInfoWithDepById(Long id) {
        SystemUserEntity systemUserEntity = systemUserMapper.selectById(id);

        SystemUserItemVo userItemVo = new SystemUserItemVo();
        BeanUtils.copyProperties(systemUserEntity, userItemVo);
        userItemVo.setDepartmentName("产品研发部");
        return userItemVo;
    }

    @Override
    public SystemUserEntity getById(Long id) {
        return systemUserMapper.selectById(id);
    }
}
