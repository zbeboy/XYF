package com.rongxingyn.xyf.web.vo.backstage.goods.datum

import javax.validation.constraints.*

open class DatumEditVo {
    @NotBlank(message = "商品ID不能为空")
    var goodsId: String? = null
    @NotBlank(message = "商品名不能为空")
    @Size(max = 100, message = "商品名在100个字符以内")
    var goodsName: String? = null
    var goodsPrice: Double? = null
    var goodsBrief: String? = null
    @Max(5, message = "推荐度最大5")
    var goodsRecommend: Int? = null
    var goodsSerial: Int? = null
    var goodsIsDel: Byte? = null
    @NotNull(message = "类别ID不能为空")
    @Min(1, message = "类别ID不正确")
    var classifyId: Int? = null
    @NotBlank(message = "商品图片不能为空")
    @Size(max = 500, message = "商品图片在150个字符以内")
    var goodsPic: String? = null
}