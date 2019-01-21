package com.rongxingyn.xyf.service.system

import com.rongxingyn.xyf.domain.tables.pojos.Users
import com.rongxingyn.xyf.web.bean.backstage.users.UsersBean
import com.rongxingyn.xyf.web.utils.DataTablesUtils
import org.jooq.Record
import org.jooq.Result

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
     * 分页查询
     *
     * @param dataTablesUtils datatables工具类
     * @return 分页数据
     */
    fun findAllByPage(dataTablesUtils: DataTablesUtils<UsersBean>): Result<Record>

    /**
     * 总数
     *
     * @return 总数
     */
    fun countAll(): Int

    /**
     * 根据条件查询总数
     *
     * @return 条件查询总数
     */
    fun countByCondition(dataTablesUtils: DataTablesUtils<UsersBean>): Int

    /**
     * 更新
     *
     * @param users 数据
     */
    fun update(users: Users)

    /**
     * 通过id更新状态
     *
     * @param ids   ids
     * @param disabled is_del
     */
    fun updateDisabled(ids: List<String>, disabled: Byte)

    /**
     * 通过id更新状态
     *
     * @param ids   ids
     * @param accountLocked lock
     */
    fun updateLocked(ids: List<String>, accountLocked: Byte)

    /**
     * 保存
     *
     * @param users 数据
     */
    fun save(users: Users)
}