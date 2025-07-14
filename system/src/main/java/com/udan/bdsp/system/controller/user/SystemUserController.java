package com.udan.bdsp.system.controller.user;

import com.udan.bdsp.common.interceptor.AuthenticationInterceptor;
import com.udan.bdsp.common.result.Result;
import com.udan.bdsp.system.service.SystemUserService;
import com.udan.bdsp.system.vo.SystemUserItemVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @Operation(summary = "根据ID查询用户信息", security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("user/info")
    public Result<SystemUserItemVo> getCurrentUserInfo(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute(AuthenticationInterceptor.USER_ID_ATTR);
        SystemUserItemVo systemUserItemVo = systemUserService.getInfoWithDepById(userId);
        return Result.ok(systemUserItemVo);
    }
}