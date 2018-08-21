package com.rongxingyn.xyf.advice

import org.springframework.security.web.reactive.result.view.CsrfRequestDataValueProcessor.DEFAULT_CSRF_ATTR_NAME
import org.springframework.security.web.server.csrf.CsrfToken
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@ControllerAdvice
class CsrfControllerAdvice {

    @ModelAttribute
    fun csrfToken(exchange: ServerWebExchange): Mono<CsrfToken> {
        val csrfToken = exchange.getAttribute<Mono<CsrfToken>>(CsrfToken::class.java.name)
        return csrfToken!!.doOnSuccess { token -> exchange.attributes[DEFAULT_CSRF_ATTR_NAME] = token }
    }
}