package com.udan.bdsp.integration.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.udan.bdsp.common.interceptor.AuthenticationInterceptor;
import com.udan.bdsp.common.utils.Result;
import com.udan.bdsp.integration.dto.SaveOrUpdateDataSourceDTO;
import com.udan.bdsp.integration.dto.SyncDataSourcePageQueryDTO;
import com.udan.bdsp.integration.dto.UpdateDataSourceStatusDTO;
import com.udan.bdsp.integration.entity.SyncDataSourceEntity;
import com.udan.bdsp.integration.service.SyncDataSourceService;
import com.udan.bdsp.integration.vo.SyncDataSourceInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 同步数据源 Controller
 * @Author TOM FORD
 * @Date 2025-07-16 13:42:49
 */
@Tag(name = "数据源管理")
@RestController
@RequestMapping("/api/integration")
@RequiredArgsConstructor
public class SyncDataSourceController {

    private final SyncDataSourceService dataSourceService;

    @Operation(summary = "根据条件分页查询数据源列表", security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("pageDataSource")
    public Result<IPage<SyncDataSourceInfoVO>> pageDataSourceInfo(@RequestParam long current, @RequestParam long size,
            SyncDataSourcePageQueryDTO queryDTO) {
        Page<SyncDataSourceInfoVO> page = new Page<>(current, size);
        IPage<SyncDataSourceInfoVO> result = dataSourceService.pageDataSourceInfo(page, queryDTO);
        return Result.ok(result);
    }

    @Operation(summary = "根据ID修改数据源可用状态", security = @SecurityRequirement(name = "Authorization"))
    @PostMapping("updateDataSourceStatusById")
    public Result<Void> updateDataSourceStatusById(@RequestBody UpdateDataSourceStatusDTO statusDTO) {
        LambdaUpdateWrapper<SyncDataSourceEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SyncDataSourceEntity::getId, statusDTO.getId()).set(SyncDataSourceEntity::getStatus,
                statusDTO.getStatus());
        dataSourceService.update(updateWrapper);
        return Result.ok();
    }

    @Operation(summary = "保存或更新数据源", security = @SecurityRequirement(name = "Authorization"))
    @PostMapping("savaOrUpdateDataSource")
    public Result<Void> savaOrUpdateDataSource(@RequestBody SaveOrUpdateDataSourceDTO sourceDTO,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute(AuthenticationInterceptor.USER_ID_ATTR);
        dataSourceService.savaOrUpdateDataSource(sourceDTO, userId);
        return Result.ok();
    }

    @Operation(summary = "根据ID删除数据源", security = @SecurityRequirement(name = "Authorization"))
    @DeleteMapping("deleteDataSource/{id}")
    public Result<Void> deleteDataSource(@PathVariable Long id) {
        dataSourceService.removeById(id);
        return Result.ok();
    }
}