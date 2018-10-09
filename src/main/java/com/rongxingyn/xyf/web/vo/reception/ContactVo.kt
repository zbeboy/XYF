package com.rongxingyn.xyf.web.vo.reception

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

open class ContactVo {
    @NotNull(message = "联系人姓名不能为空")
    @Size(max = 20, message = "联系人姓名为1~20个字符")
    var customerName: String? = null
    @NotNull(message = "联系方式不能为空")
    @Size(max = 100, message = "联系方式为1~100个字符")
    var customerContact: String? = null
    @NotNull(message = "内容不能为空")
    @Size(max = 500, message = "内容为1~500个字符")
    var content: String? = null
}