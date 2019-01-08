package com.rongxingyn.xyf.service.system

import com.rongxingyn.xyf.domain.tables.pojos.Users

/**
 * Created by zbeboy 2017-11-19 .
 **/
interface UsersService {

    /**
     * 根据用户名获取Users表完整信息
     *
     * @param username 用户账号
     * @return 用户信息
     */
    fun findByUsername(username: String): Users?

    /**
     * 更新
     *
     * @param users 数据
     */
    fun update(users: Users)

    /**
     * 保存
     *
     * @param users 数据
     */
    fun save(users: Users)
}