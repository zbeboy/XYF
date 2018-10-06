package com.rongxingyn.xyf.web.vo.backstage.feedback

import javax.validation.constraints.NotNull

open class FeedbackRemarkVo {
    @NotNull(message = "反馈处理ID不能为空")
    var feedbackId: Int? = null
    var remark: String? = null
}