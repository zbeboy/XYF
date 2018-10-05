package com.rongxingyn.xyf.web.vo.backstage.goods.datum

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

open class DatumStateVo {
    @NotBlank(message = "商品ID不能为空")
    var goodsIds: String? = null
    @NotNull(message = "状态不能为空")
    var goodsIsDel: Byte? = 0
}