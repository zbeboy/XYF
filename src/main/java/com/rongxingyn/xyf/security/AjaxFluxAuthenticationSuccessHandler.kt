package com.rongxingyn.xyf.security

import org.apache.commons.lang3.BooleanUtils
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.util.ObjectUtils
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Component
class AjaxFluxAuthenticationSuccessHandler : ServerAuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(webFilterExchange: WebFilterExchange?, authentication: Authentication?): Mono<Void> {
        val response = webFilterExchange!!.exchange.response
        var code = AjaxAuthenticationCode.OK_CODE
        val principal = authentication!!.principal
        if (!ObjectUtils.isEmpty(principal) && principal is UserDetails) {
            when {
                BooleanUtils.isFalse(principal.isEnabled) -> code = AjaxAuthenticationCode.USERNAME_IS_ENABLES
                BooleanUtils.isFalse(principal.isAccountNonExpired) -> code = AjaxAuthenticationCode.USERNAME_IS_EXPIRED
                BooleanUtils.isFalse(principal.isAccountNonLocked) -> code = AjaxAuthenticationCode.USERNAME_IS_LOCKED
                BooleanUtils.isFalse(principal.isCredentialsNonExpired) -> code = AjaxAuthenticationCode.CREDENTIALS_EXPIRED
            }
        } else {
            code = AjaxAuthenticationCode.AU_ERROR_CODE
        }
        val buffer = response.bufferFactory().allocateBuffer().write(code.toString().toByteArray())
        return response.writeAndFlushWith(Flux.just(Flux.just(buffer)))
    }
}