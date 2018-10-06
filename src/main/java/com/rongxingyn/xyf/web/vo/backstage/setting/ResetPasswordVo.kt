package com.rongxingyn.xyf.web.vo.backstage.setting

import javax.validation.constraints.Pattern

open class ResetPasswordVo {
    @Pattern(regexp = "^[a-zA-Z0-9]\\w{5,17}\$",message = "密码为数据字母6~18位")
    var newPassword: String? = null
    var okPassword: String? = null
}