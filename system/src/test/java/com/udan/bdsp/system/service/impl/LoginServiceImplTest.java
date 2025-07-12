package com.udan.bdsp.system.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * @Description
 * @Author TOM FORD
 * @Date 2025-07-12 15:52:50
 */
@SpringBootTest(classes = LoginServiceImplTest.class)
class LoginServiceImplTest {
    @Test
    public void test() {
        String string = DigestUtils.md5Hex("123456");
        System.out.printf(string);
    }
}