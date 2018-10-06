package com.rongxingyn.xyf.web.backstage.website

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import reactor.core.publisher.Mono

@Controller
@RequestMapping("/web/backstage/website")
open class WebsiteViewController {

    /**
     * 网站设置页
     *
     * @return list page
     */
    @GetMapping("/list")
    fun list(): Mono<String> {
        return Mono.just("backstage/website/website_list")
    }
}