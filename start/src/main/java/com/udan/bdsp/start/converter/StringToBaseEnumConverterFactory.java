package com.udan.bdsp.start.converter;

import com.udan.bdsp.common.enums.BaseEnum;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

/**
 * @Description
 **
 * 将请求参数中的字符串自动转换为实现了 BaseEnum 接口的枚举类实例。
 * 适用于 Spring MVC 在处理 GET/POST 请求时自动将参数映射为枚举类型。
 * <p>
 * 例如：?status=1 可自动转换为 StatusEnum 中 code=1 的枚举值。
 * <p>
 * ⚠️ 使用前提：
 *   - 所有枚举类需实现 BaseEnum 接口，并提供 getCode() 方法。
 *   - 该类被 Spring 自动扫描（@Component）、 WebMvcConfigurer 手动注册。
 * @Author TOM FORD
 * @Date 2025-07-21 19:33:10
 */
@Component
public class StringToBaseEnumConverterFactory implements ConverterFactory<String, BaseEnum> {

    @NotNull
    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(@NotNull Class<T> targetType) {
        return new  Converter<String, T>() {

            @Override
            public T convert(@NotNull String code) {
                T[] enumConstants = targetType.getEnumConstants();
                for (T value : enumConstants) {
                    if (value.getCode().toString().equals(code)) {
                        return value;
                    }
                }
                throw new IllegalArgumentException();
            }
        };
    }
}
