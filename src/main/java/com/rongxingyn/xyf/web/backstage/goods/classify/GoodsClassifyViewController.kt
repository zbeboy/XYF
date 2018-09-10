package com.rongxingyn.xyf.web.backstage.goods.classify

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import reactor.core.publisher.Mono

@Controller
open class GoodsClassifyViewController {

    /**
     * 商品分类列表页
     *
     * @return list page
     */
    @GetMapping("/web/goods/classify/list")
    fun root(): Mono<String> {
        return Mono.just("backstage/goods/classify/classify_list")
    }
}