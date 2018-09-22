package com.rongxingyn.xyf.web.vo.backstage.goods.classify

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

open class ClassifyStateVo {
    @NotBlank(message = "类别ID不能为空")
    var classifyIds: String? = null
    @NotNull(message = "状态不能为空")
    var classifyIsDel: Byte? = 0
}