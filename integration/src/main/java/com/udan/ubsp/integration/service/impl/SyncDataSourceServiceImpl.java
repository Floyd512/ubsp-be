package com.udan.ubsp.integration.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.udan.ubsp.integration.dto.SaveOrUpdateDataSourceDTO;
import com.udan.ubsp.integration.dto.SyncDataSourcePageQueryDTO;
import com.udan.ubsp.integration.entity.SyncDataSourceEntity;
import com.udan.ubsp.integration.mapper.SyncDataSourceMapper;
import com.udan.ubsp.integration.service.SyncDataSourceService;
import com.udan.ubsp.integration.vo.SyncDataSourceInfoVO;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Description 针对表【ubsp_datasource(数据源表)】的数据库操作Service实现
 * @Author TOM FORD
 * @Date 2025-07-16 13:46:39
 */
@Service
public class SyncDataSourceServiceImpl extends ServiceImpl<SyncDataSourceMapper, SyncDataSourceEntity> implements SyncDataSourceService {

    @Autowired
    private SyncDataSourceMapper sourceMapper;

    @Override
    public IPage<SyncDataSourceInfoVO> pageDataSourceInfo(Page<SyncDataSourceInfoVO> page, SyncDataSourcePageQueryDTO queryDTO) {
        return sourceMapper.pageDataSourceInfo(page, queryDTO);
    }

    @Override
    public void savaOrUpdateDataSource(SaveOrUpdateDataSourceDTO sourceDTO, Long userId) {
        SyncDataSourceEntity dataSourceEntity = new SyncDataSourceEntity();
        BeanUtils.copyProperties(sourceDTO, dataSourceEntity);

        // 密码加密处理
        if (sourceDTO.getPassword() != null) {
            dataSourceEntity.setPassword(DigestUtils.md5Hex(sourceDTO.getPassword()));
        }

        boolean isUpdate = sourceDTO.getId() != null;
        if (isUpdate) {
            dataSourceEntity.setUpdatedId(userId);
        } else {
            dataSourceEntity.setCreatedId(userId);
        }
        super.saveOrUpdate(dataSourceEntity);
    }
}