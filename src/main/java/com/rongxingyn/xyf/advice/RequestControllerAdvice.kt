package com.rongxingyn.xyf.advice

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@ControllerAdvice
class RequestControllerAdvice {

    @ModelAttribute
    fun webPath(exchange: ServerWebExchange): Mono<ServerWebExchange> {
        val request = exchange.request
        exchange.attributes["webPath"] = request.uri.scheme + "://" + request.uri.host + ":" + request.uri.port + request.path.contextPath().value()
        return Mono.just(exchange)
    }
}