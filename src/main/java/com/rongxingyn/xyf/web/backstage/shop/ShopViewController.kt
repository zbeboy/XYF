package com.rongxingyn.xyf.web.backstage.shop

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import reactor.core.publisher.Mono

@Controller
@RequestMapping("/web/backstage/shop")
open class ShopViewController {

    /**
     * 店铺资料页
     *
     * @return list page
     */
    @GetMapping("/list")
    fun list(): Mono<String> {
        return Mono.just("backstage/shop/shop_list")
    }
}