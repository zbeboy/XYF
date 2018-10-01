package com.rongxingyn.xyf.service.utils

import com.rongxingyn.xyf.config.Workbook
import org.springframework.stereotype.Component

/**
 * Created by zbeboy 2017-11-30 .
 **/
@Component
open class RequestUtils {

    companion object {

        /**
         * 获取realPath
         *
         * @return real path.
         */
        @JvmStatic
        fun getRealPath(): String {
            return System.getProperty("java.io.tmpdir") + Workbook.DIRECTORY_SPLIT
        }
    }
}