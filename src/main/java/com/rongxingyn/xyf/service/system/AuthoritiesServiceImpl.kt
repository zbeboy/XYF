package com.rongxingyn.xyf.service.system

import com.rongxingyn.xyf.domain.tables.daos.AuthoritiesDao
import com.rongxingyn.xyf.domain.tables.pojos.Authorities
import org.springframework.security.authentication.AuthenticationTrustResolverImpl
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.ObjectUtils
import javax.annotation.Resource

/**
 * Created by zbeboy 2017-11-17 .
 **/
@Service("authoritiesService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
open class AuthoritiesServiceImpl : AuthoritiesService {

    @Resource
    open lateinit var authoritiesDao: AuthoritiesDao

    override fun findByUsername(username: String): List<Authorities> {
        return authoritiesDao.fetchByUsername(username)
    }

    override fun isAnonymousAuthenticated(): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication
        return !ObjectUtils.isEmpty(authentication) && AuthenticationTrustResolverImpl().isAnonymous(authentication)
    }

    override fun save(authorities: Authorities) {
        authoritiesDao.insert(authorities)
    }

}