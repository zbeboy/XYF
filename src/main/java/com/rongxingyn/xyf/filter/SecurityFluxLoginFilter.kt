package com.rongxingyn.xyf.filter

import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

class SecurityFluxLoginFilter : WebFilter {

    override fun filter(serverWebExchange: ServerWebExchange?, webFilterChain: WebFilterChain?): Mono<Void> {
        val request = serverWebExchange!!.request
        val response = serverWebExchange.response
        println(" request method : " + request.method)
        println(" request uri : " + request.uri.path)

        val username = request.queryParams.toSingleValueMap()["username"]
        println(" request username : $username")

        if ("POST".equals(request.method!!.name, ignoreCase = true) && request.uri.path.endsWith("/login")) {
            /*val password = StringUtils.trimWhitespace(request.getParameter("password"))

            if (!StringUtils.hasLength(username)) {// 邮箱不为空
                response.writer.print(AjaxAuthenticationCode.USERNAME_IS_BLANK)
                return
            }

            if (!StringUtils.hasLength(password)) {// 密码不为空
                response.writer.print(AjaxAuthenticationCode.PASSWORD_IS_BLANK)
                return
            }

            val context = request.session.servletContext
            val ctx = WebApplicationContextUtils
                    .getWebApplicationContext(context)
            val usersService = ctx!!
                    .getBean("usersService") as UsersService
            val users = usersService.findByUsername(username)

            if (ObjectUtils.isEmpty(users)) {// 用户是否存在
                response.writer.print(AjaxAuthenticationCode.USERNAME_IS_NOT_EXIST_CODE)
                return
            }

            if (ObjectUtils.isEmpty(users!!.disabled) || users.disabled == 1.toByte()) {// 用户是否已被注销
                response.writer.print(AjaxAuthenticationCode.USERNAME_IS_ENABLES)
                return
            }*/
        }
        return webFilterChain!!.filter(serverWebExchange)
    }
}