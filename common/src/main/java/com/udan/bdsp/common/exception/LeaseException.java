package com.udan.bdsp.common.exception;

import com.udan.bdsp.common.result.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description
 * @Author TOM FORD
 * @Date 2025-07-12 15:06:13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LeaseException extends RuntimeException {
    private Integer code;

    public LeaseException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public LeaseException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }
}