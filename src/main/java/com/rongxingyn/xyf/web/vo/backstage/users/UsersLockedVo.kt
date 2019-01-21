package com.rongxingyn.xyf.web.vo.backstage.users

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

open class UsersLockedVo {
    @NotBlank(message = "账号不能为空")
    var userIds: String? = null
    @NotNull(message = "状态不能为空")
    var accountLocked: Byte? = 0
}