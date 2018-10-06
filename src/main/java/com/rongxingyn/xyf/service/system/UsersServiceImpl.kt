package com.rongxingyn.xyf.service.system

import com.rongxingyn.xyf.domain.tables.daos.UsersDao
import com.rongxingyn.xyf.domain.tables.pojos.Users
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
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

    override fun update(users: Users) {
        usersDao.update(users)
    }
}