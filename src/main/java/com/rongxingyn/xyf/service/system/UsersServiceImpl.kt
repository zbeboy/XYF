package com.rongxingyn.xyf.service.system

import com.rongxingyn.xyf.domain.Tables.*
import com.rongxingyn.xyf.domain.tables.daos.UsersDao
import com.rongxingyn.xyf.domain.tables.pojos.Users
import com.rongxingyn.xyf.domain.tables.records.GoodsRecord
import com.rongxingyn.xyf.domain.tables.records.UsersRecord
import com.rongxingyn.xyf.service.plugin.DataTablesPlugin
import com.rongxingyn.xyf.service.utils.SQLQueryUtils
import com.rongxingyn.xyf.web.bean.backstage.goods.datum.GoodsBean
import com.rongxingyn.xyf.web.bean.backstage.users.UsersBean
import com.rongxingyn.xyf.web.utils.DataTablesUtils
import org.jooq.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.ObjectUtils
import org.springframework.util.StringUtils
import javax.annotation.Resource


/**
 * Created by zbeboy 2017-11-19 .
 **/
@Service("usersService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
open class UsersServiceImpl @Autowired constructor(dslContext: DSLContext) : DataTablesPlugin<UsersBean>(), UsersService {

    private val create: DSLContext = dslContext

    @Resource
    open lateinit var usersDao: UsersDao

    override fun findByUsername(username: String): Users? {
        return usersDao.findById(username)
    }

    override fun findAllByPage(dataTablesUtils: DataTablesUtils<UsersBean>): Result<Record> {
        return dataPagingQueryAll(dataTablesUtils, create, USERS)
    }

    override fun countAll(): Int {
        return statisticsAll(create, USERS)
    }

    override fun countByCondition(dataTablesUtils: DataTablesUtils<UsersBean>): Int {
        return statisticsWithCondition(dataTablesUtils, create, USERS)
    }

    override fun update(users: Users) {
        usersDao.update(users)
    }

    override fun updateDisabled(ids: List<String>, disabled: Byte) {
        for (id in ids) {
            create.update<UsersRecord>(USERS).set<Byte>(USERS.DISABLED, disabled).where(USERS.USERNAME.eq(id)).execute()
        }
    }

    override fun updateLocked(ids: List<String>, accountLocked: Byte) {
        for (id in ids) {
            create.update<UsersRecord>(USERS).set<Byte>(USERS.ACCOUNT_LOCKED, accountLocked).where(USERS.USERNAME.eq(id)).execute()
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    override fun save(users: Users) {
        usersDao.insert(users)
    }

    /**
     * 数据全局搜索条件
     *
     * @param dataTablesUtils datatables工具类
     * @return 搜索条件
     */
    override fun searchCondition(dataTablesUtils: DataTablesUtils<UsersBean>): Condition? {
        var a: Condition? = null
        val search = dataTablesUtils.search
        if (!ObjectUtils.isEmpty(search)) {
            val username = StringUtils.trimWhitespace(search!!.getString("username"))
            val realName = StringUtils.trimWhitespace(search.getString("realName"))
            if (StringUtils.hasLength(username)) {
                a = USERS.USERNAME.like(SQLQueryUtils.likeAllParam(username))
            }

            if (StringUtils.hasLength(realName)) {
                a = if (ObjectUtils.isEmpty(a)) {
                    USERS.REAL_NAME.like(SQLQueryUtils.likeAllParam(realName))
                } else {
                    a!!.and(USERS.REAL_NAME.like(SQLQueryUtils.likeAllParam(realName)))
                }
            }
        }
        return a
    }

    /**
     * 数据排序
     *
     * @param dataTablesUtils     datatables工具类
     * @param selectConditionStep 条件
     */
    override fun sortCondition(dataTablesUtils: DataTablesUtils<UsersBean>, selectConditionStep: SelectConditionStep<Record>?, selectJoinStep: SelectJoinStep<Record>?, type: Int) {
        val orderColumnName = dataTablesUtils.orderColumnName
        val orderDir = dataTablesUtils.orderDir
        val isAsc = "asc".equals(orderDir, ignoreCase = true)
        var sortField: Array<SortField<*>?>? = null
        if (StringUtils.hasLength(orderColumnName)) {

            if ("username".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(1)
                if (isAsc) {
                    sortField[0] = USERS.USERNAME.asc()
                } else {
                    sortField[0] = USERS.USERNAME.desc()
                }
            }

            if ("real_name".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(2)
                if (isAsc) {
                    sortField[0] = USERS.REAL_NAME.asc()
                    sortField[1] = USERS.USERNAME.asc()
                } else {
                    sortField[0] = USERS.REAL_NAME.desc()
                    sortField[1] = USERS.USERNAME.desc()
                }
            }

            if ("sex".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(2)
                if (isAsc) {
                    sortField[0] = USERS.SEX.asc()
                    sortField[1] = USERS.USERNAME.asc()
                } else {
                    sortField[0] = USERS.SEX.desc()
                    sortField[1] = USERS.USERNAME.desc()
                }
            }

            if ("contact".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(2)
                if (isAsc) {
                    sortField[0] = USERS.CONTACT.asc()
                    sortField[1] = USERS.USERNAME.asc()
                } else {
                    sortField[0] = USERS.CONTACT.desc()
                    sortField[1] = USERS.USERNAME.desc()
                }
            }

            if ("address".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(2)
                if (isAsc) {
                    sortField[0] = USERS.ADDRESS.asc()
                    sortField[1] = USERS.USERNAME.asc()
                } else {
                    sortField[0] = USERS.ADDRESS.desc()
                    sortField[1] = USERS.USERNAME.desc()
                }
            }

            if ("disabled".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(2)
                if (isAsc) {
                    sortField[0] = USERS.DISABLED.asc()
                    sortField[1] = USERS.USERNAME.asc()
                } else {
                    sortField[0] = USERS.DISABLED.desc()
                    sortField[1] = USERS.USERNAME.desc()
                }
            }

            if ("account_expired".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(2)
                if (isAsc) {
                    sortField[0] = USERS.ACCOUNT_EXPIRED.asc()
                    sortField[1] = USERS.USERNAME.asc()
                } else {
                    sortField[0] = USERS.ACCOUNT_EXPIRED.desc()
                    sortField[1] = USERS.USERNAME.desc()
                }
            }

            if ("account_locked".equals(orderColumnName, ignoreCase = true)) {
                sortField = arrayOfNulls(2)
                if (isAsc) {
                    sortField[0] = USERS.ACCOUNT_LOCKED.asc()
                    sortField[1] = USERS.USERNAME.asc()
                } else {
                    sortField[0] = USERS.ACCOUNT_LOCKED.desc()
                    sortField[1] = USERS.USERNAME.desc()
                }
            }

        }

        sortToFinish(selectConditionStep, selectJoinStep, type, *sortField!!)
    }
}