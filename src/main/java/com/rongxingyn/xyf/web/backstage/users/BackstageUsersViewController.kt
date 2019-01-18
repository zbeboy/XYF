package com.rongxingyn.xyf.web.backstage.users

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import reactor.core.publisher.Mono

@Controller
@RequestMapping("/web/backstage/users")
open class BackstageUsersViewController {

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