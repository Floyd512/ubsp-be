package com.udan.bdsp.integration.service;

import com.udan.bdsp.integration.vo.SourceTypeWithNodeVO;

import java.util.List;

/**
 * @Description 数据源类型服务接口
 * @Author TOM FORD
 * @Date 2025-08-06
 */
public interface DataSourceTypeService {

    List<SourceTypeWithNodeVO> getDataSourceTypesWithNodeTypes();
}