package com.rongxingyn.xyf.web.backstage.goods.datum

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import reactor.core.publisher.Mono

@Controller
@RequestMapping("/web/goods")
open class GoodsDatumViewController {

    /**
     * 商品列表页
     *
     * @return list page
     */
    @GetMapping("/datum/list")
    fun list(): Mono<String> {
        return Mono.just("backstage/goods/datum/datum_list")
    }

    /**
     * 商品添加页
     *
     * @return add page
     */
    @GetMapping("/datum/add")
    fun add(): Mono<String> {
        return Mono.just("backstage/goods/datum/datum_add")
    }
}