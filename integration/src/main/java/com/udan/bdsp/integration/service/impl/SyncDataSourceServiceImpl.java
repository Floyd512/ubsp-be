package com.udan.bdsp.integration.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.udan.bdsp.integration.dto.SaveOrUpdateDataSourceDTO;
import com.udan.bdsp.integration.dto.SyncDataSourcePageQueryDTO;
import com.udan.bdsp.integration.entity.SyncDataSourceEntity;
import com.udan.bdsp.integration.mapper.SyncDataSourceMapper;
import com.udan.bdsp.integration.service.SyncDataSourceService;
import com.udan.bdsp.integration.vo.SyncDataSourceInfoVO;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Description 针对表【bdsp_datasource(数据源表)】的数据库操作Service实现
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
        
        // 处理用户名，如果为空字符串则设置为null
        if (sourceDTO.getUsername() != null && sourceDTO.getUsername().trim().isEmpty()) {
            dataSourceEntity.setUsername(null);
        }
        
        // 处理密码加密，只有当密码不为空时才设置密码字段
        if (sourceDTO.getPassword() != null && !sourceDTO.getPassword().trim().isEmpty()) {
            dataSourceEntity.setPassword(DigestUtils.md5Hex(sourceDTO.getPassword()));
        }
        // 如果密码为空或null，则不设置password字段，Entity中password保持为null
        
        boolean isUpdate = sourceDTO.getId() != null;
        if (isUpdate) {
            dataSourceEntity.setUpdatedId(userId);
        } else {
            dataSourceEntity.setCreatedId(userId);
        }
        super.saveOrUpdate(dataSourceEntity);
    }
}