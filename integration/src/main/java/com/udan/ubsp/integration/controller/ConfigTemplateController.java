package com.udan.ubsp.integration.controller;

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

    @Operation(summary = "获取配置模板列表", security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("/configTemplates")
    public Result<List<ConfigTemplateVO>> getConfigTemplates() {
        List<ConfigTemplateVO> templates = configTemplateService.getEnabledTemplates();
        return Result.ok(templates);
    }
}