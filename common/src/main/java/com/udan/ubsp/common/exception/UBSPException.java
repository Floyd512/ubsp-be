package com.udan.ubsp.common.exception;

import com.udan.ubsp.common.enums.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description UBSP业务异常类
 * @Author TOM FORD
 * @Date 2025-07-12 15:06:13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UBSPException extends RuntimeException {
    private Integer code;

    public UBSPException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public UBSPException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }
}