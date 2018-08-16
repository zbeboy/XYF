package com.rongxingyn.xyf.service.system

import com.rongxingyn.xyf.domain.tables.daos.UsersDao
import com.rongxingyn.xyf.domain.tables.pojos.Users
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.ObjectUtils
import javax.annotation.Resource


/**
 * Created by zbeboy 2017-11-19 .
 **/
@Service("usersService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
open class UsersServiceImpl : UsersService {

    @Resource
    open lateinit var usersDao: UsersDao

    override fun findByUsername(username: String): Users? {
        return usersDao.findById(username)
    }

    override fun getUsernameFromSession(): String? {
        val principal = SecurityContextHolder.getContext().authentication.principal
        var username: String? = null
        if (!ObjectUtils.isEmpty(principal) && principal is UserDetails) {
            username = principal.username
        }
        return username
    }
}