package com.rongxingyn.xyf.mobile.vo.users

import javax.validation.constraints.NotBlank

open class LoginVo {
    @NotBlank(message = "账号不能为空")
    var username: String? = null
    @NotBlank(message = "密码不能为空")
    var password: String? = null
}