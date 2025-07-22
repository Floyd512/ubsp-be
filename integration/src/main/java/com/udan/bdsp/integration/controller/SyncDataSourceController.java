package com.udan.bdsp.integration.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.udan.bdsp.common.result.Result;
import com.udan.bdsp.integration.dto.SyncDataSourcePageQueryDTO;
import com.udan.bdsp.integration.enums.DataSourceTypeEnum;
import com.udan.bdsp.integration.service.SyncDataSourceService;
import com.udan.bdsp.integration.vo.SyncDataSourceTypeVO;
import com.udan.bdsp.integration.vo.SyncDataSourceInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 同步数据源Controller
 * @Author TOM FORD
 * @Date 2025-07-16 13:42:49
 */
@Tag(name = "02-数据集成")
@RestController
@RequestMapping("/api/integration")
public class SyncDataSourceController {

    @Autowired
    private SyncDataSourceService dataSourceService;

    @Operation(summary = "根据条件分页查询数据源列表")
    @GetMapping("pageDataSource")
    public Result<IPage<SyncDataSourceInfoVO>> pageDataSourceInfo(@RequestParam long current, @RequestParam long size, SyncDataSourcePageQueryDTO queryDTO) {
        Page<SyncDataSourceInfoVO> page = new Page<>(current, size);
        IPage<SyncDataSourceInfoVO> result = dataSourceService.pageDataSourceInfo(page, queryDTO);
        return Result.ok(result);
    }

    @Operation(summary = "获取数据源类型列表")
    @GetMapping("dataSourceTypes")
    public Result<List<SyncDataSourceTypeVO>> getDataSourceTypes() {
        List<SyncDataSourceTypeVO> dataSourceTypes = Arrays.stream(DataSourceTypeEnum.values())
                .map(type -> new SyncDataSourceTypeVO(
                        type.getCode(),
                        type.getName(),
                        type.getDefaultPort()
                ))
                .collect(Collectors.toList());
        return Result.ok(dataSourceTypes);
    }
}