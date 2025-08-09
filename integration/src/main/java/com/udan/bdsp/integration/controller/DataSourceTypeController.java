package com.udan.bdsp.integration.controller;

import com.udan.bdsp.common.utils.Result;
import com.udan.bdsp.integration.service.DataSourceTypeService;
import com.udan.bdsp.integration.vo.SourceTypeWithNodeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 数据集成 Controller
 * @Author TOM FORD
 * @Date 2025-08-06
 */
@Tag(name = "数据源类型管理")
@RestController
@RequestMapping("/api/integration")
@RequiredArgsConstructor
public class DataSourceTypeController {

    private final DataSourceTypeService dataSourceTypeService;

    @Operation(summary = "获取数据源类型及支持的节点类型", description = "同时获取数据源类型列表和支持的节点类型", security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("/data-source-types/with-node-types")
    public Result<List<SourceTypeWithNodeVO>> getDataSourceTypesWithNodeTypes() {
        List<SourceTypeWithNodeVO> dataSourceTypes = dataSourceTypeService.getDataSourceTypesWithNodeTypes();
        return Result.ok(dataSourceTypes);
    }
}