package com.udan.bdsp.system.controller.login;

import com.udan.bdsp.common.utils.Result;
import com.udan.bdsp.system.dto.LoginDTO;
import com.udan.bdsp.system.service.LoginService;
import com.udan.bdsp.system.vo.CaptchaVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 登录Controller
 * @Author TOM FORD
 * @Date 2025-07-03 20:43:08
 */

@Tag(name = "01-系统管理")
@RestController
@RequestMapping("/api/system")
public class LoginController {

    @Autowired
    private LoginService service;

    @Operation(summary = "获取图形验证码")
    @GetMapping("captcha")
    public Result<CaptchaVo> getCaptcha() {
        CaptchaVo captchaVo = service.getCaptcha();
        return Result.ok(captchaVo);
    }


    @Operation(summary = "登录")
    @PostMapping("login")
    public Result<String> login(@RequestBody LoginDTO loginDTO) {
        String jwt = service.login(loginDTO);
        return Result.ok(jwt);
    }
}