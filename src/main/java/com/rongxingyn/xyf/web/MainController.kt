package com.rongxingyn.xyf.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import reactor.core.publisher.Mono

@Controller
open class MainController {

    /**
     * main page
     *
     * @return main page
     */
    @RequestMapping("/")
    fun root(): Mono<String> {
        return Mono.just("index")
    }

    /**
     * Home page.
     *
     * @return home page
     */
    @RequestMapping("/index")
    fun index(): Mono<String> {
        return Mono.just("index")
    }

    /**
     * 登录页
     *
     * @return 登录页.
     */
    @GetMapping("/login")
    fun login(): Mono<String> {
        return Mono.just("login")
    }
}