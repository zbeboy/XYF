package com.rongxingyn.xyf.web.vo.backstage.goods.banner

import javax.validation.constraints.NotNull

open class BannerSerialVo {
    @NotNull(message = "Banner ID不能为空")
    var bannerId: String? = null
    var bannerSerial: Int? = 0
}