package com.udan.bdsp.start.config;

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
        "com.udan.bdsp.system.mapper",           // 系统管理模块（包含用户、角色、菜单、权限等）
        "com.udan.bdsp.integration.**.mapper",   // 数据集成模块（包含数据源、同步任务、执行记录等）
//        "com.udan.bdsp.datadev.mapper",         // 数据开发模块（包含SQL查询、脚本管理等）
//        "com.udan.bdsp.analysis.mapper",        // 数据分析模块（包含报表、仪表板等）
//        "com.udan.bdsp.platform.**.mapper"     // 平台服务模块（包含元数据、血缘、质量等）
})
public class MybatisPlusConfiguration {

    /**
     * MyBatis-Plus分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加分页插件，指定数据库类型为MySQL
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}