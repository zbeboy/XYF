package com.rongxingyn.xyf.config

import com.rongxingyn.xyf.filter.SecurityFluxLoginFilter
import com.rongxingyn.xyf.security.AjaxFluxAuthenticationFailureHandler
import com.rongxingyn.xyf.security.AjaxFluxAuthenticationSuccessHandler
import com.rongxingyn.xyf.security.MyReactiveUserDetailsServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.reactive.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import javax.inject.Inject

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
open class SecurityFluxConfiguration {

    @Inject
    open lateinit var ajaxFluxAuthenticationSuccessHandler: AjaxFluxAuthenticationSuccessHandler

    @Inject
    open lateinit var ajaxFluxAuthenticationFailureHandler: AjaxFluxAuthenticationFailureHandler

    @Autowired
    private lateinit var myReactiveUserDetailsService: MyReactiveUserDetailsServiceImpl

    /**
     * 在这里加BEAN，会自动选择该Encoder
     */
    @Bean
    open fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    open fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
                .authorizeExchange()
                .matchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .pathMatchers("/pic/**").permitAll()
                .pathMatchers("/", "/plugin/**", "/data/**", "/user/**", Workbook.LOGIN_PAGE).permitAll()
                .pathMatchers("/web/**").hasRole("ADMIN")
                .pathMatchers("/mobile/login", "/mobile/register", "/mobile/user").permitAll()
                .pathMatchers("/mobile/backstage/**").permitAll()
                .and()
                .httpBasic()
                .and()
                .addFilterAt(SecurityFluxLoginFilter(), SecurityWebFiltersOrder.FORM_LOGIN) /*暂时因获取不到用户数据，先不用*/
                .formLogin().loginPage(Workbook.LOGIN_PAGE)
                .authenticationSuccessHandler(ajaxFluxAuthenticationSuccessHandler)
                .authenticationFailureHandler(ajaxFluxAuthenticationFailureHandler)
                .and()
                .logout()
                .and()
                .build()
    }

    /**
     * 在这里加Bean，会自动采用你自己的方式进行用户操作
     */
    @Bean
    open fun reactiveUserDetailsService(): ReactiveUserDetailsService {
        return myReactiveUserDetailsService
    }

}