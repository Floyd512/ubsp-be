package com.udan.bdsp.system.service;

import com.udan.bdsp.system.dto.LoginDTO;
import com.udan.bdsp.system.vo.CaptchaVo;

/**
 * @Description 登录Service接口
 * @Author TOM FORD
 * @Date 2025-07-12 13:34:47
 */
public interface LoginService {
    CaptchaVo getCaptcha();

    String login(LoginDTO loginDTO);
}