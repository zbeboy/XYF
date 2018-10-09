package com.rongxingyn.xyf.web.reception

import com.rongxingyn.xyf.service.reception.ReceptionService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import reactor.core.publisher.Mono
import javax.annotation.Resource

@Controller
@RequestMapping("/user")
open class ReceptionViewController {

    @Resource
    open lateinit var receptionService: ReceptionService

    /**
     * 关于
     *
     * @return about page
     */
    @GetMapping("/about")
    fun list(model: Model): Mono<String> {
        receptionService.getShopInfo(model)
        return Mono.just("reception/about")
    }
}