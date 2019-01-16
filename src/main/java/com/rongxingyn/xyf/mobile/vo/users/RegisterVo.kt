package com.rongxingyn.xyf.mobile.vo.users

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

open class RegisterVo {
    @NotBlank(message = "账号不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]\\w{3,10}\$", message = "账号为数字字母3~10位")
    var username: String? = null
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]\\w{5,17}\$", message = "密码为数字字母6~18位")
    var password: String? = null
    var sex: String? = null
    var address: String? = null
    var realName: String? = null
    var contact: String? = null
    var photo: String? = null
}