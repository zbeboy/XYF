package com.rongxingyn.xyf.security

import org.springframework.http.HttpMethod
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.util.*

@Component
class MyRequireCsrfProtectionMatcher : ServerWebExchangeMatcher {

    companion object {
        val ALLOWED_METHODS = HashSet(Arrays.asList<HttpMethod>(HttpMethod.GET, HttpMethod.HEAD, HttpMethod.TRACE, HttpMethod.OPTIONS))
        val NOT_NEED_CSRF = HashSet(Arrays.asList<String>("/mobile"))
    }

    override fun matches(exchange: ServerWebExchange): Mono<ServerWebExchangeMatcher.MatchResult> {
//        return Mono.just<ServerHttpRequest>(exchange.request).map<HttpMethod> { r -> r.method }.filter { m -> ALLOWED_METHODS.contains(m) }.flatMap { m -> ServerWebExchangeMatcher.MatchResult.notMatch() }.switchIfEmpty(ServerWebExchangeMatcher.MatchResult.match())
        return Mono.just(exchange).map<ServerHttpRequest> { r -> r.request }.filter { m -> NOT_NEED_CSRF.any { r -> r.startsWith(m.uri.path, false) } || ALLOWED_METHODS.contains(m.method) }
                .flatMap { _ -> ServerWebExchangeMatcher.MatchResult.notMatch() }.switchIfEmpty(ServerWebExchangeMatcher.MatchResult.match())
    }
}