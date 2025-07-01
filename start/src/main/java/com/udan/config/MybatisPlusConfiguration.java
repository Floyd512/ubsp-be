package com.udan.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description MyBatis-Plus配置
 * 配置位置：start模块，便于扫描所有业务模块的Mapper接口
 * @Author TOM FORD
 * @Date 2025-07-01 20:36:36
 */
@Configuration
@MapperScan({
        "com.udan.bdsp.system.mapper",           // 系统管理模块
        "com.udan.bdsp.data-integration.**.mapper",   // 数据集成模块（包含子包）
//        "com.udan.bdsp.datadev.mapper",         // 数据开发模块
//        "com.udan.bdsp.analysis.mapper",        // 数据分析模块
})
public class MybatisPlusConfiguration {

    /**
     *
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加分页插件，指定数据库类型为MySQL
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}