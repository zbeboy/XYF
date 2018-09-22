package com.rongxingyn.xyf.web.vo.backstage.goods.classify

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

open class ClassifyStateVo {
    var classifyIds: String? = null
    var classifyIsDel: Byte? = 0
}