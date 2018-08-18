package com.rongxingyn.xyf

import com.rongxingyn.xyf.config.XYFProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

/**
 * 初始化密码: govern MUt6N8ha
 */
@EnableCaching
@SpringBootApplication
@EnableConfigurationProperties(XYFProperties::class)
open class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}