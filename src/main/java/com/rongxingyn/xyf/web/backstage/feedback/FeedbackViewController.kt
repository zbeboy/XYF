package com.rongxingyn.xyf.web.backstage.feedback

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import reactor.core.publisher.Mono

@Controller
@RequestMapping("/web/backstage/feedback")
open class FeedbackViewController {

    /**
     * 反馈处理页
     *
     * @return list page
     */
    @GetMapping("/list")
    fun list(): Mono<String> {
        return Mono.just("backstage/feedback/feedback_list")
    }

}