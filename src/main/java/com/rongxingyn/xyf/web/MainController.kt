package com.rongxingyn.xyf.web

import com.rongxingyn.xyf.service.reception.ReceptionService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import reactor.core.publisher.Mono
import javax.annotation.Resource

@Controller
open class MainController {

    @Resource
    open lateinit var receptionService: ReceptionService

    /**
     * main page
     *
     * @return main page
     */
    @RequestMapping("/")
    fun root(model: Model): Mono<String> {
        model.addAttribute("banners", receptionService.getBanners())
        model.addAttribute("classifies", receptionService.getClassifies())
        receptionService.getWebsiteInfo(model)
        return Mono.just("index")
    }

    /**
     * Home page.
     *
     * @return home page
     */
    @RequestMapping("/index")
    fun index(model: Model): Mono<String> {
        model.addAttribute("banners", receptionService.getBanners())
        model.addAttribute("classifies", receptionService.getClassifies())
        receptionService.getWebsiteInfo(model)
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

    /**
     * 后台欢迎页
     *
     * @return 后台欢迎页
     */
    @GetMapping("/web/backstage")
    open fun backstage(): Mono<String> {
        return Mono.just("backstage")
    }
}