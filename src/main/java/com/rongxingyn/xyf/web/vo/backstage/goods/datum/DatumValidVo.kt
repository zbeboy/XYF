package com.rongxingyn.xyf.web.vo.backstage.goods.datum

import com.rongxingyn.xyf.web.vo.ValidVo
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

open class DatumValidVo : ValidVo() {
    var goodsId: String? = null
    @NotBlank(message = "商品名不能为空")
    @Size(max = 100, message = "商品名在100个字符以内")
    var goodsName: String? = null
}