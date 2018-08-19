package com.rongxingyn.xyf.filter

import com.rongxingyn.xyf.config.Workbook
import com.rongxingyn.xyf.security.AjaxAuthenticationCode
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers
import org.springframework.util.ObjectUtils
import org.springframework.util.StringUtils
import org.springframework.web.context.support.WebApplicationContextUtils
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class SecurityFluxLoginFilter : WebFilter {

    private var requiresLogin: ServerWebExchangeMatcher = ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, Workbook.LOGIN_PAGE)

    override fun filter(serverWebExchange: ServerWebExchange, webFilterChain: WebFilterChain): Mono<Void> {
        return this.requiresLogin.matches(serverWebExchange).filter { result -> result.isMatch }
                .switchIfEmpty(webFilterChain.filter(serverWebExchange).then(Mono.empty()))
                .map { serverWebExchange }.flatMap { result ->
                    processing(result, webFilterChain)
                }
    }

    private fun processing(result: ServerWebExchange, webFilterChain: WebFilterChain): Mono<Void> {
        val username = result.formData.map { s -> s.getFirst(Workbook.USERNAME_PARAMETER) }.block()
        val password = result.formData.map { s -> s.getFirst(Workbook.PASSWORD_PARAMETER) }.block()
        val response = result.response

        if (!StringUtils.hasLength(username)) {// 邮箱不为空
            val buffer = response.bufferFactory().allocateBuffer().write(AjaxAuthenticationCode.USERNAME_IS_BLANK.toString().toByteArray())
            return response.writeAndFlushWith(Flux.just(Flux.just(buffer)))
        }

        if (!StringUtils.hasLength(password)) {// 密码不为空
            val buffer = response.bufferFactory().allocateBuffer().write(AjaxAuthenticationCode.PASSWORD_IS_BLANK.toString().toByteArray())
            return response.writeAndFlushWith(Flux.just(Flux.just(buffer)))
        }

        return webFilterChain.filter(result)
    }
}