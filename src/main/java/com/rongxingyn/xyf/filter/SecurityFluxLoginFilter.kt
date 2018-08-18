package com.rongxingyn.xyf.filter

import org.springframework.http.HttpMethod
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

class SecurityFluxLoginFilter : WebFilter {

    private var requiresLogin: ServerWebExchangeMatcher = ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, *arrayOf("/login"))

    override fun filter(serverWebExchange: ServerWebExchange, webFilterChain: WebFilterChain): Mono<Void> {

        return this.requiresLogin.matches(serverWebExchange).filter { result -> result.isMatch }
                .switchIfEmpty(webFilterChain.filter(serverWebExchange).then(Mono.empty()))
                .map { serverWebExchange }.flatMap { result ->
                    webFilterChain.filter(result)
                }
    }
}