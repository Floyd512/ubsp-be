package com.udan.ubsp.integration.controller;

import com.udan.ubsp.common.enums.ResultCodeEnum;
import com.udan.ubsp.common.utils.Result;
import com.udan.ubsp.integration.service.ConfigTemplateService;
import com.udan.ubsp.integration.vo.ConfigTemplateVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 数据源配置模版管理
 * @Author TOM FORD
 * @Date 2025-08-07 16:08:37
 */
@Tag(name = "数据源配置模版管理")
@RestController
@RequestMapping("/api/integration")
@RequiredArgsConstructor
public class ConfigTemplateController {
    private final ConfigTemplateService configTemplateService;


    @Operation(summary = "根据数据源类型和节点类型获取单个配置模板", description = "用户拖拽组件时调用", security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("/config-template/{typeCode}/{nodeType}")
    public Result<ConfigTemplateVO> getConfigTemplate(@PathVariable Integer typeCode, @PathVariable Integer nodeType) {
        ConfigTemplateVO template = configTemplateService.getByTypeCodeAndNodeType(typeCode, nodeType);
        if (template == null) {
            return Result.fail(ResultCodeEnum.INTEGRATION_CONFIG_TEMPLATE_NOT_FOUND.getCode(),
                    ResultCodeEnum.INTEGRATION_CONFIG_TEMPLATE_NOT_FOUND.getMessage());
        }
        return Result.ok(template);
    }

    @Operation(summary = "获取配置模板列表", description = "根据条件获取配置模板", security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("/config-templates")
    public Result<List<ConfigTemplateVO>> getConfigTemplates(
            @RequestParam(required = false) Integer typeCode,
            @RequestParam(required = false) Integer nodeType) {

        List<ConfigTemplateVO> templates;
        if (typeCode != null && nodeType != null) {
            // 获取单个模板
            ConfigTemplateVO template = configTemplateService.getByTypeCodeAndNodeType(typeCode, nodeType);
            templates = template != null ? List.of(template) : List.of();
        } else if (typeCode != null) {
            // 根据数据源类型获取
            templates = configTemplateService.getByTypeCode(typeCode);
        } else if (nodeType != null) {
            // 根据节点类型获取
            templates = configTemplateService.getByNodeType(nodeType);
        } else {
            // 获取所有
            templates = configTemplateService.getEnabledTemplates();
        }

        return Result.ok(templates);
    }
}