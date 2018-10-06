package com.rongxingyn.xyf.web.vo.backstage.feedback

import javax.validation.constraints.NotNull

open class FeedbackDealVo {
    @NotNull(message = "反馈处理ID不能为空")
    var feedbackId: Int? = null
    @NotNull(message = "状态不能为空")
    var hasDeal: Byte? = null
}