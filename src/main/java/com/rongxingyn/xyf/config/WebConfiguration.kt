package com.rongxingyn.xyf.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer
import java.io.File
import javax.inject.Inject


@Configuration
open class WebConfiguration : WebFluxConfigurer {

    @Inject
    open lateinit var env: Environment

    @Autowired
    open lateinit var xyfProperties: XYFProperties

    @Bean
    open fun undertow(): UndertowServletWebServerFactory {
        val undertow = UndertowServletWebServerFactory()
        if (this.env.acceptsProfiles(Workbook.SPRING_PROFILE_PRODUCTION)) {
            val documentRoot = File(System.getProperty("user.dir") + Workbook.DIRECTORY_SPLIT + xyfProperties.getConstants().documentRoot)
            if (!documentRoot.exists()) {
                documentRoot.mkdirs()
            }
            undertow.documentRoot = documentRoot
        }
        return undertow
    }

    // app 跨域请求
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/mobile/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowCredentials(true)
    }

}