package com.udan.bdsp.system.service.impl;

import com.udan.bdsp.common.constant.RedisConstant;
import com.udan.bdsp.common.exception.LeaseException;
import com.udan.bdsp.common.enums.ResultCodeEnum;
import com.udan.bdsp.common.utils.JwtUtil;
import com.udan.bdsp.system.dto.LoginDTO;
import com.udan.bdsp.system.entity.SystemUserEntity;
import com.udan.bdsp.common.enums.BaseStatusEnum;
import com.udan.bdsp.system.mapper.SystemUserMapper;
import com.udan.bdsp.system.service.LoginService;
import com.udan.bdsp.system.vo.CaptchaVo;
import com.wf.captcha.SpecCaptcha;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Description 登录Service接口实现类
 * @Author TOM FORD
 * @Date 2025-07-12 13:46:58
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Override
    public CaptchaVo getCaptcha() {
        SpecCaptcha captcha = new SpecCaptcha(130, 48, 4);
        String key = RedisConstant.SYSTEM_LOGIN_PREFIX + UUID.randomUUID();
        String value = captcha.text().toLowerCase();
        stringRedisTemplate.opsForValue().set(key, value, RedisConstant.SYSTEM_LOGIN_CAPTCHA_TTL_SEC, TimeUnit.SECONDS);
        return new CaptchaVo(captcha.toBase64(), key);
    }

    @Override
    public String login(LoginDTO loginDTO) {
        if (!StringUtils.hasLength(loginDTO.getCaptchaCode())) {
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_NOT_FOUND);
        }

        String code = stringRedisTemplate.opsForValue().get(loginDTO.getCaptchaKey());
        if (!StringUtils.hasLength(code)) {
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_EXPIRED);
        }

        if (!code.equals(loginDTO.getCaptchaCode().toLowerCase())) {
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_ERROR);
        }

        SystemUserEntity systemUserEntity = systemUserMapper.selectOneByUsername(loginDTO.getUsername());

        if (systemUserEntity == null) {
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_NOT_EXIST_ERROR);
        }

        if (systemUserEntity.getAccountStatus() == BaseStatusEnum.DISABLE) {
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_DISABLED_ERROR);
        }

        if (!systemUserEntity.getPassword().equals(DigestUtils.md5Hex(loginDTO.getPassword()))) {
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_ERROR);
        }

        //创建JWT
        return JwtUtil.createToken(systemUserEntity.getId(), systemUserEntity.getUsername());
    }
}