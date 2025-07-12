package com.udan.bdsp.system.controller.user;

import com.udan.bdsp.common.result.Result;
import com.udan.bdsp.system.service.SystemUserService;
import com.udan.bdsp.system.vo.SystemUserItemVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 用户Controller
 * @Author TOM FORD
 * @Date 2025-07-12 16:34:21
 */
@Tag(name = "01-系统管理")
@RestController
@RequestMapping("/api/system")
public class SystemUserController {

    @Autowired
    private SystemUserService systemUserService;

    @Operation(summary = "根据ID查询用户信息")
    @GetMapping("getById")
    public Result<SystemUserItemVo> getById(@RequestParam Long id) {
        SystemUserItemVo systemUserItemVo = systemUserService.getInfoWithDepById(id);
        return Result.ok(systemUserItemVo);
    }
}