package com.rongxingyn.xyf.web.vo.backstage.goods.banner

import javax.validation.constraints.NotNull

open class BannerHideVo {
    @NotNull(message = "Banner ID不能为空")
    var bannerId: String? = null
    @NotNull(message = "Hide不能为空")
    var bannerIsHide: Byte? = null
}