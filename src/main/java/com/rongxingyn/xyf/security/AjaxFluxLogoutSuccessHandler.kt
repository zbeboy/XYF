package com.rongxingyn.xyf.security

import org.springframework.security.core.Authentication
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class AjaxFluxLogoutSuccessHandler: ServerLogoutSuccessHandler {
    override fun onLogoutSuccess(webFilterExchange: WebFilterExchange?, authentication: Authentication?): Mono<Void> {
        val response = webFilterExchange!!.exchange.response
        val buffer = response.bufferFactory().allocateBuffer().write(AjaxAuthenticationCode.OK_CODE.toString().toByteArray())
        return response.writeAndFlushWith(Flux.just(Flux.just(buffer)))
    }
}