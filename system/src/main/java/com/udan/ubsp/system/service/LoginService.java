package com.udan.ubsp.system.service;

import com.udan.ubsp.system.dto.LoginDTO;
import com.udan.ubsp.system.vo.CaptchaVo;

/**
 * @Description 登录Service接口
 * @Author TOM FORD
 * @Date 2025-07-12 13:34:47
 */
public interface LoginService {
    CaptchaVo getCaptcha();

    String login(LoginDTO loginDTO);
}