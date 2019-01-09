package com.rongxingyn.xyf.config

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Spring boot 配置属性加载.
 *
 * @author zbeboy
 * @version 1.0
 * @since 1.0
 */
@ConfigurationProperties(prefix = "xyf", ignoreUnknownFields = false)
class XYFProperties {

    private val constants = Constants()

    private val security = Security()

    fun getConstants(): Constants {
        return constants
    }

    fun getSecurity(): Security {
        return security
    }

    /**
     * 通用初始化参数
     */
    class Constants {

        var documentRoot: String? = null

        var fileFreeSpace: Long? = null

        var fileMaxSize: Long? = null

        var staticImages: String? = null
    }

    /**
     * Security初始化参数
     */
    class Security {
        var desDefaultKey: String? = null
    }

}