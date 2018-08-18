package com.rongxingyn.xyf.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import javax.servlet.http.HttpServletRequest

@Controller
open class MainController {

    /**
     * main page
     *
     * @return main page
     */
    @RequestMapping("/")
    fun root(): String {
        return "index"
    }

    /**
     * Home page.
     *
     * @return home page
     */
    @RequestMapping("/index")
    fun index(): String {
        return "index"
    }

    /**
     * 登录页
     *
     * @return 登录页.
     */
    @GetMapping("/login")
    fun login(error:String?): String {
        return  "login"
    }
}