package com.rongxingyn.xyf.web.system

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import reactor.core.publisher.Mono
import java.security.Principal

@Controller
@RequestMapping("/web/backstage/users")
open class UsersViewController {

    /**
     * 反馈处理页
     *
     * @return list page
     */
    @GetMapping("/setting")
    fun setting(principal: Principal, model: Model): Mono<String> {
        model.addAttribute("username", principal.name)
        return Mono.just("backstage/setting/setting")
    }

    /**
     * 用户管理列表页
     *
     * @return list page
     */
    @GetMapping("/list")
    fun list(): Mono<String> {
        return Mono.just("backstage/users/users_list")
    }
}