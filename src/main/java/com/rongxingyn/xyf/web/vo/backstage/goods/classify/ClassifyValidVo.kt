package com.rongxingyn.xyf.web.vo.backstage.goods.classify

import com.rongxingyn.xyf.web.vo.ValidVo
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

open class ClassifyValidVo : ValidVo() {
    var classifyId: Int? = null
    @NotBlank(message = "类别名不能为空")
    @Size(max = 30, message = "类别名在30个字符以内")
    var classifyName: String? = null
}