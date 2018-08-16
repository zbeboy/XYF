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
}