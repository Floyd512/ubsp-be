package com.udan.bdsp.integration.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.udan.bdsp.integration.dto.SyncDataSourcePageQueryDTO;
import com.udan.bdsp.integration.entity.SyncDataSourceEntity;
import com.udan.bdsp.integration.vo.SyncDataSourceInfoVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description 针对表【ubsp_datasource】的数据库操作Mapper
 * @Author TOM FORD
 * @Date 2025-07-16 13:53:07
 */
@Mapper
public interface SyncDataSourceMapper extends BaseMapper<SyncDataSourceEntity> {

    IPage<SyncDataSourceInfoVO> pageDataSourceInfo(Page<SyncDataSourceInfoVO> page, SyncDataSourcePageQueryDTO queryDTO);
}