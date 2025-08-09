package com.udan.bdsp.integration.service.impl;

import com.udan.bdsp.integration.enums.DataSourceTypeEnum;
import com.udan.bdsp.integration.mapper.DataSourceTypeMapper;
import com.udan.bdsp.integration.service.DataSourceTypeService;
import com.udan.bdsp.integration.vo.SourceTypeWithNodeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description 数据源类型服务实现
 * @Author TOM FORD
 * @Date 2025-08-06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DataSourceTypeServiceImpl implements DataSourceTypeService {

    private final DataSourceTypeMapper dataSourceTypeMapper;

    @Override
    public List<SourceTypeWithNodeVO> getDataSourceTypesWithNodeTypes() {
        // 获取支持的节点类型映射
        List<Map<String, Object>> nodeTypesResult = dataSourceTypeMapper.selectSupportedNodeTypes();
        Map<Integer, List<Integer>> nodeTypesMap = nodeTypesResult.stream()
                .collect(Collectors.toMap(
                        item -> (Integer) item.get("type_code"),
                        item -> {
                            String supportedNodeTypesStr = (String) item.get("supported_node_types");
                            if (supportedNodeTypesStr != null && !supportedNodeTypesStr.isEmpty()) {
                                String[] nodeTypes = supportedNodeTypesStr.split(",");
                                return Arrays.stream(nodeTypes)
                                        .map(String::trim)
                                        .map(Integer::parseInt)
                                        .collect(Collectors.toList());
                            } else {
                                return List.of();
                            }
                        }
                ));

        // 构建合并的VO
        return Arrays.stream(DataSourceTypeEnum.values())
                .map(enumType -> buildDataSourceTypeWithNodeTypesVO(enumType, nodeTypesMap))
                .collect(Collectors.toList());
    }

    /**
     * 构建数据源类型及支持的节点类型VO
     */
    private SourceTypeWithNodeVO buildDataSourceTypeWithNodeTypesVO(DataSourceTypeEnum enumType, Map<Integer, List<Integer>> nodeTypesMap) {
        SourceTypeWithNodeVO vo = new SourceTypeWithNodeVO();
        vo.setCode(enumType.getCode());
        vo.setName(enumType.getName());
        vo.setCategoryCode(enumType.getCategory().getCode());
        vo.setCategoryName(enumType.getCategory().getName());
        vo.setDefaultPort(enumType.getDefaultPort());
        vo.setRequiresAuth(enumType.requiresAuth());
        vo.setRequiresDatabase(enumType.requiresDatabase());
        vo.setStatus(1); // 枚举中的都是启用状态
        vo.setSupportedNodeTypes(nodeTypesMap.getOrDefault(enumType.getCode(), List.of()));
        return vo;
    }
}