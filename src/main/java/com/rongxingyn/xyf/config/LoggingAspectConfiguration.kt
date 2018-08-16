package com.rongxingyn.xyf.config

import com.rongxingyn.xyf.aop.logging.LoggingAspect
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.context.annotation.Profile

/**
 * 日志切面环境配置.
 *
 * @author zbeboy
 * @version 1.0
 * @since 1.0
 */
@Configuration
@EnableAspectJAutoProxy
open class LoggingAspectConfiguration {

    /**
     * 仅在开发环境打印所有日志
     *
     * @return
     */
    @Bean
    @Profile(Workbook.SPRING_PROFILE_DEVELOPMENT)
    open fun loggingAspect(): LoggingAspect {
        return LoggingAspect()
    }
}