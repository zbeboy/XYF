package com.rongxingyn.xyf.advice

import com.rongxingyn.xyf.config.XYFProperties
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import javax.annotation.Resource

@ControllerAdvice
open class RequestControllerAdvice {

    @Resource
    open lateinit var xyfProperties: XYFProperties

    /**
     * 添加全局变量
     */
    @ModelAttribute
    fun globalVar(exchange: ServerWebExchange): Mono<ServerWebExchange> {
        val request = exchange.request
        exchange.attributes["webPath"] = request.uri.scheme + "://" + request.uri.host + ":" + request.uri.port + request.path.contextPath().value()
        exchange.attributes["fileMaxSize"] = xyfProperties.getConstants().fileMaxSize
        return Mono.just(exchange)
    }
}