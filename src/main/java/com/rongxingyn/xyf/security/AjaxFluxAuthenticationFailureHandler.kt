package com.rongxingyn.xyf.security

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class AjaxFluxAuthenticationFailureHandler : ServerAuthenticationFailureHandler {
    override fun onAuthenticationFailure(webFilterExchange: WebFilterExchange?, authenticationException: AuthenticationException?): Mono<Void> {
        val response = webFilterExchange!!.exchange.response
        val buffer = response.bufferFactory().allocateBuffer().write(AjaxAuthenticationCode.AU_ERROR_CODE.toString().toByteArray())
        return response.writeAndFlushWith(Flux.just(Flux.just(buffer)))
    }
}