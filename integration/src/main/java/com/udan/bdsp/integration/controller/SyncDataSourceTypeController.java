package com.udan.bdsp.integration.controller;

import com.udan.bdsp.common.result.Result;
import com.udan.bdsp.common.result.ResultCodeEnum;
import com.udan.bdsp.integration.service.SyncDataSourceTypeService;
import com.udan.bdsp.integration.vo.SyncDataSourceTypeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 数据源类型 Controller
 * @Author TOM FORD
 * @Date 2025-08-06
 */
@Tag(name = "数据源类型管理", description = "数据源类型相关接口")
@RestController
@RequestMapping("/api/integration")
@RequiredArgsConstructor
public class SyncDataSourceTypeController {

    private final SyncDataSourceTypeService syncDataSourceTypeService;

    @Operation(summary = "获取所有数据源类型", description = "获取系统支持的所有数据源类型配置", security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("/dataSourceTypes")
    public Result<List<SyncDataSourceTypeVO>> getDataSourceTypes() {
        List<SyncDataSourceTypeVO> dataSourceTypes = syncDataSourceTypeService.getAllDataSourceTypes();
        return Result.ok(dataSourceTypes);
    }

    @Operation(summary = "获取指定数据源类型", description = "根据类型编码获取数据源类型配置", security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("/dataSourceTypes/{typeCode}")
    public Result<SyncDataSourceTypeVO> getDataSourceType(@PathVariable Integer typeCode) {
        SyncDataSourceTypeVO dataSourceType = syncDataSourceTypeService.getDataSourceType(typeCode);
        if (dataSourceType == null) {
            return Result.fail(ResultCodeEnum.INTEGRATION_UNSUPPORTED_DATA_TYPE.getCode(),
                    ResultCodeEnum.INTEGRATION_UNSUPPORTED_DATA_TYPE.getMessage());
        }
        return Result.ok(dataSourceType);
    }

    @Operation(summary = "刷新配置缓存", description = "刷新数据源类型配置缓存", security = @SecurityRequirement(name = "Authorization"))
    @PostMapping("/dataSourceTypes/refresh")
    public Result<Void> refreshConfigCache() {
        syncDataSourceTypeService.refreshConfigCache();
        return Result.ok();
    }

    @Operation(summary = "验证数据源类型", description = "检查指定的数据源类型编码是否有效", security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("/dataSourceTypes/{typeCode}/validate")
    public Result<Boolean> validateDataSourceType(@PathVariable Integer typeCode) {
        boolean isValid = syncDataSourceTypeService.isValidDataSourceType(typeCode);
        return Result.ok(isValid);
    }
}