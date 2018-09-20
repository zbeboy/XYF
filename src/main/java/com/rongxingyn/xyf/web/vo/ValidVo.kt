package com.rongxingyn.xyf.web.vo

import javax.validation.constraints.NotNull


/**
 * 验证工具
 */
open class ValidVo {
    /**
     * 验证类型
     *
     * 0:添加
     * 1:编辑
     */
    @NotNull(message = "验证类型不能为空")
    var type: Int? = null
}