package com.rongxingyn.xyf.web.backstage.goods.banner

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import reactor.core.publisher.Mono

@Controller
@RequestMapping("/web/backstage/goods")
open class GoodsBannerViewController {

    /**
     * banner列表页
     *
     * @return list page
     */
    @GetMapping("/banner/list")
    fun list(): Mono<String> {
        return Mono.just("backstage/goods/banner/banner_list")
    }
}