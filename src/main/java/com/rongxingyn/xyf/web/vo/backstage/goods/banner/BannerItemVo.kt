package com.rongxingyn.xyf.web.vo.backstage.goods.banner

import javax.validation.constraints.NotNull

open class BannerItemVo {
    @NotNull(message = "Banner ID不能为空")
    var bannerId: String? = null
    var bannerItem: Int? = 0
}