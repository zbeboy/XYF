package com.rongxingyn.xyf.security

import com.rongxingyn.xyf.domain.tables.pojos.Authorities
import com.rongxingyn.xyf.service.system.AuthoritiesService
import com.rongxingyn.xyf.service.system.UsersService
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service("myReactiveUserDetailsService")
open class MyReactiveUserDetailsServiceImpl : ReactiveUserDetailsService {

    private val log = LoggerFactory.getLogger(MyReactiveUserDetailsServiceImpl::class.java)

    @Autowired
    private lateinit var usersService: UsersService

    @Autowired
    private lateinit var authoritiesService: AuthoritiesService

    override fun findByUsername(s: String): Mono<UserDetails> {
        log.debug("Username is : {}", s)
        val username = StringUtils.trim(s)
        val users = usersService.findByUsername(username)
        val authoritiesRecords = authoritiesService.findByUsername(username)
        val authorities = buildUserAuthority(authoritiesRecords)
        return Mono.justOrEmpty(User.builder()
                .username(s)
                .password(users!!.password)
                .authorities(authorities)
                .accountExpired(users.accountExpired == 1.toByte())
                .accountLocked(users.accountLocked == 1.toByte())
                .credentialsExpired(users.credentialsExpired == 1.toByte())
                .disabled(users.disabled == 1.toByte())
                .build())
    }

    /**
     * 返回验证角色
     *
     * @param authoritiesRecords 权限
     * @return 组装
     */
    private fun buildUserAuthority(authorities: List<Authorities>): List<GrantedAuthority> {
        val setAuths = authorities
                .map { SimpleGrantedAuthority(it.authority) }
                .toSet()
        return ArrayList(setAuths)
    }

}