package com.rongxingyn.xyf.config


/**
 * Application constants.
 * 开发环境配置常量
 *
 * @author zbeboy
 * @version 1.1
 * @since 1.0
 */
open class Workbook {
    companion object {
        /*
        开发环境
        */
        const val SPRING_PROFILE_DEVELOPMENT = "dev"

        /*
        生产环境
        */
        const val SPRING_PROFILE_PRODUCTION = "prod"

        /*
         目录分隔符
        */
        const val DIRECTORY_SPLIT = "/"
    }
}