package com.udan.bdsp.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.udan.bdsp.system.entity.SystemUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description 系统用户Mapper接口
 * @Author TOM FORD
 * @Date 2025-01-21 10:00:00
 */
@Mapper
public interface SystemUserMapper extends BaseMapper<SystemUser> {


    SystemUser selectOneByUsername(String username);
}