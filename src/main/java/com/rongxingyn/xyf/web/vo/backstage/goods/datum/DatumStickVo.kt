package com.rongxingyn.xyf.web.vo.backstage.goods.datum

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

open class DatumStickVo {
    @NotBlank(message = "商品ID不能为空")
    var goodsIds: String? = null
    @NotNull(message = "置顶状态不能为空")
    var goodsIsStick: Byte? = 0
}