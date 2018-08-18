package com.rongxingyn.xyf.service.system

import com.rongxingyn.xyf.domain.tables.pojos.Authorities

/**
 * Created by zbeboy 2017-11-17 .
 **/
interface AuthoritiesService {

    /**
     * 通过账号查询权限
     *
     * @username 账号
     * @return 权限
     */
    fun findByUsername(username: String): List<Authorities>

    /**
     * Check if user is login by remember me cookie, refer
     * org.springframework.security.authentication.AuthenticationTrustResolverImpl
     * @return true or false
     */
    fun isAnonymousAuthenticated(): Boolean
}