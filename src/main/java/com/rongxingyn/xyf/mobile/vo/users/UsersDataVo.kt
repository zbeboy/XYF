package com.rongxingyn.xyf.mobile.vo.users

import javax.validation.constraints.NotBlank

open class UsersDataVo {
    @NotBlank(message = "账号不能为空")
    var username: String? = null
    @NotBlank(message = "TOKEN不能为空")
    var accessToken: String? = null
}