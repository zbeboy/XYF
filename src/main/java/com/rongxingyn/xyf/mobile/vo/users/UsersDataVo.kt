package com.rongxingyn.xyf.mobile.vo.users

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

open class UsersDataVo {
    @NotBlank(message = "账号不能为空")
    var username: String? = null
    @NotBlank(message = "TOKEN不能为空")
    var accessToken: String? = null
    var realName:String? =null
    @Pattern(regexp = "^[a-zA-Z0-9]\\w{5,17}\$", message = "密码为数字字母6~18位")
    var password: String? = null
    var sex:String? = null
    var contact: String? = null
    var address: String? = null
}