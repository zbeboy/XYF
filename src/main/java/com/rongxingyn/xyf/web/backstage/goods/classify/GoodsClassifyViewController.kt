package com.rongxingyn.xyf.web.backstage.goods.classify

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import reactor.core.publisher.Mono

@Controller
@RequestMapping("/web/backstage/goods")
open class GoodsClassifyViewController {

    /**
     * 商品分类列表页
     *
     * @return list page
     */
    @GetMapping("/classify/list")
    fun list(): Mono<String> {
        return Mono.just("backstage/goods/classify/classify_list")
    }
}