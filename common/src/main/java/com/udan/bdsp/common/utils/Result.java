package com.udan.bdsp.common.utils;

import com.udan.bdsp.common.enums.ResultCodeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Description 通用响应结果类
 * @Author TOM FORD
 * @Date 2025-07-03 10:00:00
 */
@Data
@Schema(description = "响应结果")
public class Result<T> {

    // 返回码，例如 200 表示成功，500 表示失败等
    private Integer code;

    // 返回消息，例如 "成功"、"参数错误" 等
    private String message;

    // 返回的数据内容，类型为泛型 T，可支持任意对象类型
    private T data;

    /**
     * 空构造方法，默认提供，便于序列化和反序列化
     */
    public Result() {
    }

    /**
     * 内部构建方法，仅设置 data 字段
     *
     * @param data 响应中要返回的数据
     * @return 一个初始化了 data 字段的 Result 对象
     */
    private static <T> Result<T> build(T data) {
        Result<T> result = new Result<T>();
        if (data != null)
            result.setData(data);
        return result;
    }

    /**
     * 构建完整的 Result 响应对象（包含 code、message、data）
     *
     * @param body     响应数据内容
     * @param codeEnum 枚举类中定义的状态码与提示信息
     * @return 构建完成的 Result 对象
     */
    public static <T> Result<T> build(T body, ResultCodeEnum codeEnum) {
        Result<T> result = build(body);
        result.setCode(codeEnum.getCode());
        result.setMessage(codeEnum.getMessage());
        return result;
    }

    /**
     * 返回一个成功的响应（带有数据）
     *
     * @param data 要返回的数据
     * @return 包含成功状态码与数据的 Result 对象
     */
    public static <T> Result<T> ok(T data) {
        return build(data, ResultCodeEnum.COMMON_SUCCESS);
    }

    /**
     * 返回一个成功的响应（无数据）
     *
     * @return 仅包含成功状态码的 Result 对象
     */
    public static <T> Result<T> ok() {
        return Result.ok(null);
    }

    /**
     * 返回一个通用的失败响应（无数据）
     *
     * @return 包含失败状态码的 Result 对象
     */
    public static <T> Result<T> fail() {
        return build(null, ResultCodeEnum.COMMON_FAIL);
    }

    /**
     * 自定义失败响应（用于需要手动指定状态码和提示信息的场景）
     *
     * @param code    自定义的错误码
     * @param message 自定义的错误提示
     * @return 包含自定义错误信息的 Result 对象
     */
    public static <T> Result<T> fail(Integer code, String message) {
        Result<T> result = build(null);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}